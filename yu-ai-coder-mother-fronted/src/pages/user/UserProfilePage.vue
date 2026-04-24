<template>
  <div class="profile-page">
    <div class="page-header">
      <h1 class="page-title">个人中心</h1>
      <p class="page-desc">查看和编辑你的个人信息</p>
    </div>

    <div class="profile-card">
      <!-- 头像区域 -->
      <div class="avatar-section">
        <div class="avatar-wrapper" @click="editing && triggerAvatarInput()">
          <a-avatar :size="80" :src="displayAvatar" class="profile-avatar">
            <template #icon>
              <UserOutlined />
            </template>
          </a-avatar>
          <div v-if="editing" class="avatar-edit-mask">
            <CameraOutlined />
          </div>
        </div>
        <div class="avatar-info">
          <h2 class="display-name">{{ userStore.loginUser?.userName || userStore.loginUser?.userAccount }}</h2>
          <a-tag v-if="userStore.loginUser?.userRole === 'admin'" color="green">管理员</a-tag>
          <a-tag v-else color="blue">普通用户</a-tag>
        </div>
      </div>

      <a-divider class="dark-divider" />

      <!-- 查看模式 -->
      <div v-if="!editing" class="info-section">
        <div class="info-row">
          <span class="info-label">账号</span>
          <span class="info-value">{{ userStore.loginUser?.userAccount }}</span>
        </div>
        <div class="info-row">
          <span class="info-label">昵称</span>
          <span class="info-value">{{ userStore.loginUser?.userName || '-' }}</span>
        </div>
        <div class="info-row">
          <span class="info-label">简介</span>
          <span class="info-value">{{ userStore.loginUser?.userProfile || '暂无简介' }}</span>
        </div>
        <div class="info-row">
          <span class="info-label">注册时间</span>
          <span class="info-value">{{ formatDate(userStore.loginUser?.createTime) }}</span>
        </div>

        <a-button type="primary" class="edit-btn" @click="startEditing">编辑资料</a-button>
      </div>

      <!-- 编辑模式 -->
      <div v-else class="info-section">
        <a-form layout="vertical" class="edit-form">
          <a-form-item label="头像">
            <div class="avatar-edit-row">
              <a-avatar :size="64" :src="previewAvatar" class="edit-avatar">
                <template #icon>
                  <UserOutlined />
                </template>
              </a-avatar>
              <div class="avatar-edit-controls">
                <a-input
                  v-model:value="editForm.userAvatar"
                  placeholder="请输入头像图片链接"
                  class="dark-input avatar-input"
                />
                <span class="avatar-hint">支持输入图片 URL 地址</span>
              </div>
            </div>
          </a-form-item>
          <a-form-item label="昵称">
            <a-input
              v-model:value="editForm.userName"
              placeholder="请输入昵称"
              class="dark-input"
            />
          </a-form-item>
          <a-form-item label="简介">
            <a-textarea
              v-model:value="editForm.userProfile"
              placeholder="请输入个人简介"
              :rows="4"
              class="dark-input"
            />
          </a-form-item>
          <div class="form-actions">
            <a-button type="primary" class="save-btn" :loading="saving" @click="handleSave">
              保存
            </a-button>
            <a-button class="cancel-btn" @click="cancelEditing">取消</a-button>
          </div>
        </a-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue';
import { UserOutlined, CameraOutlined } from '@ant-design/icons-vue';
import { message } from 'ant-design-vue';
import { useLoginUserStore } from '@/stores/loginUser';
import { updateUser } from '@/api/user';

const userStore = useLoginUserStore();
const editing = ref(false);
const saving = ref(false);

const editForm = reactive({
  userName: '',
  userProfile: '',
  userAvatar: '',
});

// 当前展示的头像：编辑时用表单值，否则用 store 值
const displayAvatar = computed(() => userStore.loginUser?.userAvatar || undefined);
const previewAvatar = computed(() => editForm.userAvatar || undefined);

const startEditing = () => {
  editForm.userName = userStore.loginUser?.userName || '';
  editForm.userProfile = userStore.loginUser?.userProfile || '';
  editForm.userAvatar = userStore.loginUser?.userAvatar || '';
  editing.value = true;
};

const cancelEditing = () => {
  editing.value = false;
};

const handleSave = async () => {
  if (!userStore.loginUser) return;
  saving.value = true;
  try {
    await updateUser({
      id: userStore.loginUser.id,
      userName: editForm.userName,
      userProfile: editForm.userProfile,
      userAvatar: editForm.userAvatar,
    });
    message.success('保存成功');
    editing.value = false;
    await userStore.fetchLoginUser();
  } catch {
    // 错误已在 request 拦截器中处理
  } finally {
    saving.value = false;
  }
};

const triggerAvatarInput = () => {
  // 占位：点击头像时聚焦到 URL 输入框
};

const formatDate = (dateStr: string | undefined) => {
  if (!dateStr) return '-';
  return dateStr.replace('T', ' ').slice(0, 19);
};

onMounted(() => {
  if (!userStore.loginUser) {
    userStore.fetchLoginUser();
  }
});
</script>

<style scoped>
.profile-page {
  font-family: 'DM Sans', -apple-system, BlinkMacSystemFont, sans-serif;
  max-width: 600px;
  margin: 0 auto;
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

.profile-card {
  background-color: #1E293B;
  border: 1px solid #272F42;
  border-radius: 16px;
  padding: 32px;
}

/* 头像区域 */
.avatar-section {
  display: flex;
  align-items: center;
  gap: 20px;
}

.avatar-wrapper {
  position: relative;
  cursor: default;
  flex-shrink: 0;
}

.avatar-wrapper:has(.avatar-edit-mask) {
  cursor: pointer;
}

.profile-avatar {
  background-color: #0F172A;
  color: #22C55E;
}

.avatar-edit-mask {
  position: absolute;
  inset: 0;
  border-radius: 50%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #F8FAFC;
  font-size: 20px;
  opacity: 0;
  transition: opacity 0.2s;
}

.avatar-wrapper:hover .avatar-edit-mask {
  opacity: 1;
}

.avatar-info {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.display-name {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 22px;
  font-weight: 600;
  color: #F8FAFC;
  margin: 0;
}

/* 分割线 */
.dark-divider {
  border-color: #272F42;
  margin: 24px 0;
}

/* 信息展示 */
.info-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.info-row {
  display: flex;
  align-items: flex-start;
  gap: 16px;
}

.info-label {
  color: #64748B;
  font-size: 14px;
  min-width: 72px;
  flex-shrink: 0;
  padding-top: 2px;
}

.info-value {
  color: #F1F5F9;
  font-size: 14px;
  word-break: break-all;
}

.edit-btn {
  align-self: flex-start;
  margin-top: 8px;
  background: linear-gradient(135deg, #22C55E, #16A34A);
  border: none;
  border-radius: 8px;
  height: 36px;
  padding: 0 24px;
}

.edit-btn:hover {
  opacity: 0.9;
}

/* 编辑表单 */
.edit-form :deep(.ant-form-item-label > label) {
  color: #CBD5E1;
  font-size: 14px;
}

.dark-input :deep(.ant-input),
.dark-input :deep(.ant-input-affix-wrapper),
.dark-input :deep(.ant-textarea) {
  background-color: #0F172A;
  border: 1px solid #334155;
  color: #F8FAFC;
  border-radius: 8px;
}

.dark-input :deep(.ant-input::placeholder),
.dark-input :deep(.ant-textarea::placeholder) {
  color: #475569;
}

/* 头像编辑行 */
.avatar-edit-row {
  display: flex;
  align-items: center;
  gap: 16px;
}

.edit-avatar {
  background-color: #0F172A;
  color: #22C55E;
  flex-shrink: 0;
}

.avatar-edit-controls {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.avatar-input {
  width: 100%;
}

.avatar-hint {
  font-size: 12px;
  color: #64748B;
}

.form-actions {
  display: flex;
  gap: 12px;
}

.save-btn {
  background: linear-gradient(135deg, #22C55E, #16A34A);
  border: none;
  border-radius: 8px;
  height: 36px;
}

.save-btn:hover {
  opacity: 0.9;
}

.cancel-btn {
  border: 1px solid #334155;
  color: #94A3B8;
  border-radius: 8px;
  background: transparent;
  height: 36px;
}

.cancel-btn:hover {
  border-color: #475569;
  color: #F8FAFC;
}

@media (max-width: 768px) {
  .profile-card {
    padding: 24px 20px;
  }

  .avatar-section {
    flex-direction: column;
    text-align: center;
  }

  .avatar-info {
    align-items: center;
  }

  .avatar-edit-row {
    flex-direction: column;
    text-align: center;
  }

  .info-row {
    flex-direction: column;
    gap: 4px;
  }
}
</style>
