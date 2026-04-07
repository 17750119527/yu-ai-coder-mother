<template>
  <a-layout-header class="global-header">
    <div class="logo">
      <span>YU-AI-Coder</span>
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
      <a-menu-item key="features">
        <router-link to="/features">功能</router-link>
      </a-menu-item>
      <a-menu-item key="about">
        <router-link to="/about">关于</router-link>
      </a-menu-item>
    </a-menu>
    <div class="user-info">
      <a-dropdown>
        <a-button type="text" class="user-button">
          <a-avatar size="small" style="margin-right: 8px;">
            <template #icon>
              <UserOutlined />
            </template>
          </a-avatar>
          <span>用户</span>
        </a-button>
        <template #overlay>
          <a-menu>
            <a-menu-item key="profile">
              <router-link to="/profile">个人中心</router-link>
            </a-menu-item>
            <a-menu-item key="settings">
              <router-link to="/settings">设置</router-link>
            </a-menu-item>
            <a-menu-item key="logout">退出登录</a-menu-item>
          </a-menu>
        </template>
      </a-dropdown>
    </div>
  </a-layout-header>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import { UserOutlined } from '@ant-design/icons-vue';

const route = useRoute();
const selectedKeys = ref<string[]>(['home']);

// 计算当前选中的菜单项
const currentKey = computed(() => {
  const path = route.path;
  switch (path) {
    case '/':
      return 'home';
    case '/features':
      return 'features';
    case '/about':
      return 'about';
    case '/profile':
      return 'profile';
    case '/settings':
      return 'settings';
    default:
      return 'home';
  }
});

// 处理菜单点击
const handleMenuClick = (e: any) => {
  selectedKeys.value = [e.key];
};

// 监听路由变化，更新选中的菜单项
watch(() => route.path, () => {
  selectedKeys.value = [currentKey.value];
});

// 初始化时设置选中的菜单项
onMounted(() => {
  selectedKeys.value = [currentKey.value];
});
</script>

<style scoped>
.global-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  height: 64px;
  background-color: #001529;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #fff;
  margin-right: 48px;
}

.header-menu {
  flex: 1;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-button {
  color: #fff;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .logo {
    font-size: 16px;
    margin-right: 24px;
  }
  
  .header-menu {
    display: none;
  }
  
  .global-header {
    padding: 0 16px;
  }
}
</style>