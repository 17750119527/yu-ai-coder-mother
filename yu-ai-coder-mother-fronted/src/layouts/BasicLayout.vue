<template>
  <a-layout class="basic-layout">
    <GlobalHeader />

    <a-layout-content class="content-area">
      <div class="content-wrapper">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </div>
    </a-layout-content>

    <GlobalFooter />
  </a-layout>
</template>

<script setup lang="ts">
import GlobalHeader from './GlobalHeader.vue';
import GlobalFooter from './GlobalFooter.vue';
</script>

<style scoped>
.basic-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #0F172A;
}

.content-area {
  flex: 1;
  padding: 24px;
  background-color: #0F172A;
}

.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
  min-height: calc(100vh - 180px);
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

@media (max-width: 768px) {
  .content-area {
    padding: 16px;
  }
}
</style>
