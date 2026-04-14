<template>
  <a-layout-header class="global-header">
    <div class="logo" @click="router.push('/')">
      <span class="logo-text">YU</span>
      <span class="logo-sep">-</span>
      <span class="logo-sub">AI-Coder</span>
    </div>
    <a-menu
      mode="horizontal"
      theme="dark"
      :selected-keys="selectedKeys"
      @click="handleMenuClick"
      class="header-menu"
    >
      <a-menu-item key="home">
        <router-link to="/">首页</router-link>
      </a-menu-item>
      <a-menu-item v-if="isAdmin" key="admin">
        <router-link to="/admin/user-manage">用户管理</router-link>
      </a-menu-item>
    </a-menu>
    <div class="user-info">
      <!-- 已登录状态 -->
      <template v-if="loginUserStore.loginUser">
        <a-dropdown>
          <a-button type="text" class="user-button">
            <a-avatar size="small" class="user-avatar">
              <template #icon>
                <UserOutlined />
              </template>
            </a-avatar>
            <span class="user-name">{{ loginUserStore.loginUser.userName || loginUserStore.loginUser.userAccount }}</span>
          </a-button>
          <template #overlay>
            <a-menu>
              <a-menu-item key="profile" @click="router.push('/user/profile')">
                个人中心
              </a-menu-item>
              <a-menu-divider />
              <a-menu-item key="logout" @click="handleLogout">退出登录</a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </template>
      <!-- 未登录状态 -->
      <template v-else>
        <a-button type="text" class="login-button" @click="router.push('/login')">
          登录
        </a-button>
        <a-button type="primary" class="register-button" @click="router.push('/register')">
          注册
        </a-button>
      </template>
    </div>
  </a-layout-header>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { UserOutlined } from '@ant-design/icons-vue';
import { useLoginUserStore } from '@/stores/loginUser';
import { message } from 'ant-design-vue';

const route = useRoute();
const router = useRouter();
const loginUserStore = useLoginUserStore();
const selectedKeys = ref<string[]>(['home']);

const isAdmin = computed(() => loginUserStore.loginUser?.userRole === 'admin');

const currentKey = computed(() => {
  const path = route.path;
  if (path === '/') return 'home';
  if (path.startsWith('/admin')) return 'admin';
  return 'home';
});

const handleMenuClick = (e: any) => {
  selectedKeys.value = [e.key];
};

const handleLogout = async () => {
  try {
    await loginUserStore.logout();
    message.success('退出登录成功');
    router.push('/');
  } catch {
    // 错误已在 request 拦截器中处理
  }
};

watch(() => route.path, () => {
  selectedKeys.value = [currentKey.value];
});

onMounted(() => {
  selectedKeys.value = [currentKey.value];
  loginUserStore.fetchLoginUser();
});
</script>

<style scoped>
.global-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 32px;
  height: 56px;
  background-color: #0B1120;
  border-bottom: 1px solid #1E293B;
  line-height: 56px;
}

.logo {
  cursor: pointer;
  font-size: 18px;
  font-family: 'Space Grotesk', sans-serif;
  margin-right: 48px;
  display: flex;
  align-items: center;
  gap: 0;
}

.logo-text {
  color: #22C55E;
  font-weight: 700;
}

.logo-sep {
  color: #475569;
  margin: 0 2px;
}

.logo-sub {
  color: #F8FAFC;
  font-weight: 500;
}

.header-menu {
  flex: 1;
  background: transparent;
  border-bottom: none;
  line-height: 56px;
}

.header-menu :deep(.ant-menu-item) {
  color: #94A3B8;
  border-bottom: 2px solid transparent;
}

.header-menu :deep(.ant-menu-item:hover) {
  color: #F8FAFC;
}

.header-menu :deep(.ant-menu-item-selected) {
  color: #22C55E !important;
  border-bottom-color: #22C55E !important;
}

.header-menu :deep(.ant-menu-item a) {
  color: inherit;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.user-button {
  color: #F8FAFC !important;
  display: flex;
  align-items: center;
  gap: 8px;
}

.user-avatar {
  background-color: #1E293B;
  color: #22C55E;
}

.user-name {
  font-size: 14px;
}

.login-button {
  color: #94A3B8 !important;
  font-size: 14px;
}

.login-button:hover {
  color: #F8FAFC !important;
}

.register-button {
  background: linear-gradient(135deg, #22C55E, #16A34A);
  border: none;
  font-size: 14px;
  height: 32px;
  padding: 0 16px;
  border-radius: 8px;
}

.register-button:hover {
  opacity: 0.9;
}

@media (max-width: 768px) {
  .global-header {
    padding: 0 16px;
  }

  .logo {
    font-size: 16px;
    margin-right: 16px;
  }

  .header-menu {
    display: none;
  }

  .user-name {
    display: none;
  }
}
</style>
