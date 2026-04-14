import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/pages/HomePage.vue'
import UserLoginPage from '@/pages/user/UserLoginPage.vue'
import UserRegisterPage from '@/pages/user/UserRegisterPage.vue'
import UserManagePage from '@/pages/admin/UserManagePage.vue'
import UserProfilePage from '@/pages/user/UserProfilePage.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: '主页',
      component: HomePage,
    },
    {
      path: '/login',
      name: '用户登录',
      component: UserLoginPage,
    },
    {
      path: '/register',
      name: '用户注册',
      component: UserRegisterPage,
    },
    {
      path: '/admin/user-manage',
      name: '用户管理',
      component: UserManagePage,
      meta: { requiresAuth: true, requiresAdmin: true },
    },
    {
      path: '/user/profile',
      name: '个人中心',
      component: UserProfilePage,
      meta: { requiresAuth: true },
    },
  ]
})

export default router
