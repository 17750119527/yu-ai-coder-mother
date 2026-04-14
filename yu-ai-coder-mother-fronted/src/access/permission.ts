import router from '@/router';
import { useLoginUserStore } from '@/stores/loginUser';

// 白名单路由（无需登录即可访问）
const WHITE_LIST = ['/', '/login', '/register'];

router.beforeEach(async (to, _from, next) => {
  const loginUserStore = useLoginUserStore();

  // 如果还没有获取过用户信息，先尝试获取
  if (!loginUserStore.loginUser) {
    await loginUserStore.fetchLoginUser();
  }

  const isLoggedIn = !!loginUserStore.loginUser;
  const isAdmin = loginUserStore.loginUser?.userRole === 'admin';

  // 白名单路由直接放行
  if (WHITE_LIST.includes(to.path)) {
    // 已登录用户访问登录/注册页，重定向到首页
    if (isLoggedIn && (to.path === '/login' || to.path === '/register')) {
      next({ path: '/' });
      return;
    }
    next();
    return;
  }

  // 需要登录但未登录 → 跳转登录页
  if (!isLoggedIn) {
    next({ path: '/login', query: { redirect: to.fullPath } });
    return;
  }

  // 需要管理员权限但非管理员 → 跳转首页
  if (to.meta.requiresAdmin && !isAdmin) {
    next({ path: '/' });
    return;
  }

  next();
});
