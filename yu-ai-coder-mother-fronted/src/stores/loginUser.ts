import { defineStore } from 'pinia';
import { userLogin, userRegister, getLoginUser, userLogout } from '@/api/user';
import type { LoginUserVO } from '@/api/user';

export const useLoginUserStore = defineStore('loginUser', {
  state: () => ({
    loginUser: null as LoginUserVO | null,
  }),

  actions: {
    /** 用户登录 */
    async login(userAccount: string, userPassword: string) {
      const res = await userLogin({ userAccount, userPassword });
      this.loginUser = res;
      return res;
    },

    /** 用户注册 */
    async register(userAccount: string, userPassword: string, checkPassword: string) {
      return await userRegister({ userAccount, userPassword, checkPassword });
    },

    /** 用户注销 */
    async logout() {
      await userLogout();
      this.loginUser = null;
    },

    /** 从后端获取当前登录用户（页面初始化时调用） */
    async fetchLoginUser() {
      try {
        const res = await getLoginUser();
        this.loginUser = res;
      } catch {
        this.loginUser = null;
      }
    },
  },
});
