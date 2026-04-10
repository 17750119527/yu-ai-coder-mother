<template>
  <a-layout class="basic-layout">
    <!-- 顶部导航栏 -->
    <GlobalHeader />
    
    <!-- 中间内容区域 -->
    <a-layout-content class="content-area">
      <div class="content-wrapper">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </div>
    </a-layout-content>
    
    <!-- 底部版权信息 -->
    <GlobalFooter />
  </a-layout>
</template>

<script setup lang="ts">
import { defineComponent } from 'vue';
import GlobalHeader from '../components/GlobalHeader.vue';
import GlobalFooter from '../components/GlobalFooter.vue';
</script>

<style scoped>
.basic-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.content-area {
  flex: 1;
  padding: 24px;
  background-color: #f0f2f5;
}

.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  background-color: #fff;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
  min-height: calc(100vh - 240px);
}

/* 路由切换动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .content-area {
    padding: 16px;
  }
  
  .content-wrapper {
    padding: 16px;
    min-height: calc(100vh - 200px);
  }
}

@media (min-width: 769px) and (max-width: 1024px) {
  .content-area {
    padding: 20px;
  }
  
  .content-wrapper {
    padding: 20px;
  }
}
</style>