<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-card">
        <h2 class="card-title">登录</h2>
        <p class="card-desc">登录你的 YU-AI-Coder 账号</p>
        <a-form
          :model="formState"
          @finish="handleSubmit"
          layout="vertical"
          class="login-form"
        >
          <a-form-item
            label="账号"
            name="userAccount"
            :rules="[
              { required: true, message: '请输入账号' },
              { min: 4, message: '账号长度至少4位' },
            ]"
          >
            <a-input
              v-model:value="formState.userAccount"
              placeholder="请输入账号"
              size="large"
              class="dark-input"
            />
          </a-form-item>

          <a-form-item
            label="密码"
            name="userPassword"
            :rules="[
              { required: true, message: '请输入密码' },
              { min: 5, message: '密码长度至少5位' },
            ]"
          >
            <a-input-password
              v-model:value="formState.userPassword"
              placeholder="请输入密码"
              size="large"
              class="dark-input"
            />
          </a-form-item>

          <a-form-item>
            <a-button
              type="primary"
              html-type="submit"
              class="submit-btn"
              size="large"
              :loading="loading"
              block
            >
              登录
            </a-button>
          </a-form-item>

          <div class="form-footer">
            <span class="footer-text">还没有账号？</span>
            <router-link to="/register" class="footer-link">立即注册</router-link>
          </div>
        </a-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { message } from 'ant-design-vue';
import { useLoginUserStore } from '@/stores/loginUser';

const router = useRouter();
const userStore = useLoginUserStore();
const loading = ref(false);
const formState = reactive({
  userAccount: '',
  userPassword: '',
});

const handleSubmit = async () => {
  loading.value = true;
  try {
    await userStore.login(formState.userAccount, formState.userPassword);
    message.success('登录成功');
    router.push('/');
  } catch {
    // 错误已在 request 拦截器中处理
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.login-page {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: calc(100vh - 180px);
}

.login-container {
  width: 100%;
  max-width: 400px;
}

.login-card {
  background-color: #1E293B;
  border: 1px solid #272F42;
  border-radius: 16px;
  padding: 40px 32px;
}

.card-title {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 28px;
  font-weight: 700;
  color: #F8FAFC;
  margin: 0 0 8px;
}

.card-desc {
  font-size: 14px;
  color: #64748B;
  margin: 0 0 32px;
}

.login-form :deep(.ant-form-item-label > label) {
  color: #CBD5E1;
  font-size: 14px;
}

.login-form :deep(.ant-form-item) {
  margin-bottom: 20px;
}

.dark-input :deep(.ant-input),
.dark-input :deep(.ant-input-password) {
  background-color: #0F172A;
  border: 1px solid #334155;
  color: #F8FAFC;
  border-radius: 10px;
  height: 44px;
}

.dark-input :deep(.ant-input::placeholder),
.dark-input :deep(.ant-input-password input::placeholder) {
  color: #475569;
}

.dark-input :deep(.ant-input:hover),
.dark-input :deep(.ant-input-password:hover) {
  border-color: #475569;
}

.dark-input :deep(.ant-input-affix-wrapper) {
  background-color: #0F172A;
  border: 1px solid #334155;
  border-radius: 10px;
}

.dark-input :deep(.ant-input-affix-wrapper:hover) {
  border-color: #475569;
}

.dark-input :deep(.ant-input-affix-wrapper-focused) {
  border-color: #22C55E !important;
  box-shadow: 0 0 0 2px rgba(34, 197, 94, 0.15);
}

.dark-input :deep(.ant-input-focused) {
  border-color: #22C55E !important;
  box-shadow: 0 0 0 2px rgba(34, 197, 94, 0.15);
}

.submit-btn {
  height: 44px;
  border-radius: 10px;
  font-size: 15px;
  font-weight: 600;
  background: linear-gradient(135deg, #22C55E, #16A34A);
  border: none;
  margin-top: 8px;
}

.submit-btn:hover {
  opacity: 0.9;
}

.form-footer {
  text-align: center;
  margin-top: 8px;
}

.footer-text {
  color: #64748B;
  font-size: 14px;
}

.footer-link {
  color: #22C55E;
  font-size: 14px;
  margin-left: 4px;
}

.footer-link:hover {
  color: #4ADE80;
}

@media (max-width: 768px) {
  .login-card {
    margin: 0 16px;
    padding: 32px 24px;
  }
}
</style>
