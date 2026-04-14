<template>
  <div class="user-manage-page">
    <div class="page-header">
      <h1 class="page-title">用户管理</h1>
      <p class="page-desc">管理系统中的所有用户</p>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <a-input
        v-model:value="searchParams.userAccount"
        placeholder="搜索账号"
        allow-clear
        class="search-input dark-input"
        @pressEnter="handleSearch"
      />
      <a-input
        v-model:value="searchParams.userName"
        placeholder="搜索昵称"
        allow-clear
        class="search-input dark-input"
        @pressEnter="handleSearch"
      />
      <a-select
        v-model:value="searchParams.userRole"
        placeholder="角色筛选"
        allow-clear
        class="search-select dark-select"
      >
        <a-select-option value="admin">管理员</a-select-option>
        <a-select-option value="user">普通用户</a-select-option>
      </a-select>
      <a-button type="primary" class="search-btn" @click="handleSearch">搜索</a-button>
      <a-button class="reset-btn" @click="handleReset">重置</a-button>
    </div>

    <!-- 用户表格 -->
    <div class="table-card">
      <a-table
        :columns="columns"
        :data-source="userList"
        :loading="loading"
        :pagination="pagination"
        row-key="id"
        @change="handleTableChange"
        class="dark-table"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'userRole'">
            <a-tag :color="record.userRole === 'admin' ? 'green' : 'blue'">
              {{ record.userRole === 'admin' ? '管理员' : '普通用户' }}
            </a-tag>
          </template>
          <template v-if="column.key === 'createTime'">
            {{ formatDate(record.createTime) }}
          </template>
          <template v-if="column.key === 'action'">
            <a-button type="link" size="small" class="action-link" @click="openEditModal(record)">
              编辑
            </a-button>
            <a-popconfirm
              title="确定要删除该用户吗？"
              ok-text="确定"
              cancel-text="取消"
              @confirm="handleDelete(record.id)"
            >
              <a-button type="link" size="small" class="action-link action-link-danger">
                删除
              </a-button>
            </a-popconfirm>
          </template>
        </template>
      </a-table>
    </div>

    <!-- 编辑弹窗 -->
    <a-modal
      v-model:open="editModalVisible"
      title="编辑用户"
      :confirm-loading="editLoading"
      @ok="handleEditSubmit"
      class="dark-modal"
      ok-text="保存"
      cancel-text="取消"
    >
      <a-form layout="vertical" class="edit-form">
        <a-form-item label="账号">
          <a-input :value="editForm.userAccount" disabled class="dark-input" />
        </a-form-item>
        <a-form-item label="昵称">
          <a-input v-model:value="editForm.userName" placeholder="请输入昵称" class="dark-input" />
        </a-form-item>
        <a-form-item label="角色">
          <a-select v-model:value="editForm.userRole" class="dark-select">
            <a-select-option value="admin">管理员</a-select-option>
            <a-select-option value="user">普通用户</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="简介">
          <a-textarea v-model:value="editForm.userProfile" placeholder="请输入简介" :rows="3" class="dark-input" />
        </a-form-item>
        <a-form-item label="新密码（可选）">
          <a-input-password v-model:value="editForm.userPassword" placeholder="不修改请留空" class="dark-input" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { message } from 'ant-design-vue';
import { listUserByPage, deleteUser, updateUser } from '@/api/user';
import type { UserVO, UserQueryRequest, UserUpdateRequest } from '@/api/user';

const loading = ref(false);
const userList = ref<UserVO[]>([]);

const searchParams = reactive<UserQueryRequest>({
  userAccount: '',
  userName: '',
  userRole: undefined as any,
  pageNum: 1,
  pageSize: 10,
});

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条`,
});

const columns = [
  { title: '账号', dataIndex: 'userAccount', key: 'userAccount', width: 140 },
  { title: '昵称', dataIndex: 'userName', key: 'userName', width: 120 },
  { title: '角色', dataIndex: 'userRole', key: 'userRole', width: 100 },
  { title: '简介', dataIndex: 'userProfile', key: 'userProfile', ellipsis: true },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: 170 },
  { title: '操作', key: 'action', width: 140, fixed: 'right' as const },
];

// 编辑弹窗
const editModalVisible = ref(false);
const editLoading = ref(false);
const editForm = reactive<UserUpdateRequest>({
  id: 0,
  userAccount: '',
  userName: '',
  userRole: '',
  userProfile: '',
  userPassword: '',
});

// 加载用户列表
const loadData = async () => {
  loading.value = true;
  try {
    const res = await listUserByPage(searchParams);
    userList.value = res.records;
    pagination.current = res.current;
    pagination.total = res.total;
    pagination.pageSize = res.size;
  } catch {
    // 错误已在 request 拦截器中处理
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleSearch = () => {
  searchParams.pageNum = 1;
  loadData();
};

// 重置
const handleReset = () => {
  searchParams.userAccount = '';
  searchParams.userName = '';
  searchParams.userRole = undefined as any;
  searchParams.pageNum = 1;
  loadData();
};

// 表格分页变化
const handleTableChange = (pag: any) => {
  searchParams.pageNum = pag.current;
  searchParams.pageSize = pag.pageSize;
  loadData();
};

// 打开编辑弹窗
const openEditModal = (record: UserVO) => {
  editForm.id = record.id;
  editForm.userAccount = record.userAccount;
  editForm.userName = record.userName;
  editForm.userRole = record.userRole;
  editForm.userProfile = record.userProfile;
  editForm.userPassword = '';
  editModalVisible.value = true;
};

// 提交编辑
const handleEditSubmit = async () => {
  editLoading.value = true;
  try {
    await updateUser({ ...editForm });
    message.success('更新成功');
    editModalVisible.value = false;
    loadData();
  } catch {
    // 错误已在 request 拦截器中处理
  } finally {
    editLoading.value = false;
  }
};

// 删除用户
const handleDelete = async (id: number) => {
  try {
    await deleteUser(id);
    message.success('删除成功');
    loadData();
  } catch {
    // 错误已在 request 拦截器中处理
  }
};

// 格式化日期
const formatDate = (dateStr: string) => {
  if (!dateStr) return '-';
  return dateStr.replace('T', ' ').slice(0, 19);
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.user-manage-page {
  font-family: 'DM Sans', -apple-system, BlinkMacSystemFont, sans-serif;
}

.page-header {
  margin-bottom: 24px;
}

.page-title {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 28px;
  font-weight: 700;
  color: #F8FAFC;
  margin: 0 0 8px;
}

.page-desc {
  font-size: 14px;
  color: #64748B;
  margin: 0;
}

/* 搜索栏 */
.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 24px;
  flex-wrap: wrap;
}

.search-input {
  width: 200px;
}

.search-select {
  width: 140px;
}

.search-btn {
  background: linear-gradient(135deg, #22C55E, #16A34A);
  border: none;
  border-radius: 8px;
}

.search-btn:hover {
  opacity: 0.9;
}

.reset-btn {
  border: 1px solid #334155;
  color: #94A3B8;
  border-radius: 8px;
  background: transparent;
}

.reset-btn:hover {
  border-color: #475569;
  color: #F8FAFC;
}

/* 表格 */
.table-card {
  background-color: #1E293B;
  border: 1px solid #272F42;
  border-radius: 16px;
  padding: 24px;
  overflow-x: auto;
}

.dark-table :deep(.ant-table) {
  background: transparent;
  color: #F8FAFC;
}

.dark-table :deep(.ant-table-thead > tr > th) {
  background: #0F172A;
  color: #94A3B8;
  border-bottom: 1px solid #272F42;
  font-weight: 500;
}

.dark-table :deep(.ant-table-tbody > tr > td) {
  border-bottom: 1px solid #1E293B;
  color: #CBD5E1;
}

.dark-table :deep(.ant-table-tbody > tr:hover > td) {
  background: #272F4220;
}

.dark-table :deep(.ant-pagination .ant-pagination-item a) {
  color: #94A3B8;
}

.dark-table :deep(.ant-pagination .ant-pagination-item-active a) {
  color: #22C55E;
}

.dark-table :deep(.ant-pagination .ant-pagination-options .ant-select-selector) {
  background: #0F172A;
  border-color: #334155;
  color: #CBD5E1;
}

/* 操作按钮 */
.action-link {
  color: #22C55E !important;
  padding: 0 4px;
}

.action-link-danger {
  color: #EF4444 !important;
}

/* 编辑弹窗 */
.dark-input :deep(.ant-input),
.dark-input :deep(.ant-input-affix-wrapper),
.dark-input :deep(.ant-input-password) {
  background-color: #0F172A;
  border: 1px solid #334155;
  color: #F8FAFC;
  border-radius: 8px;
}

.dark-input :deep(.ant-input::placeholder) {
  color: #475569;
}

.dark-input :deep(.ant-input-disabled) {
  background-color: #1E293B;
  color: #64748B;
}

.dark-select :deep(.ant-select-selector) {
  background-color: #0F172A !important;
  border: 1px solid #334155 !important;
  color: #F8FAFC !important;
  border-radius: 8px;
}

.edit-form :deep(.ant-form-item-label > label) {
  color: #CBD5E1;
}

@media (max-width: 768px) {
  .search-bar {
    flex-direction: column;
  }

  .search-input,
  .search-select {
    width: 100%;
  }
}
</style>
