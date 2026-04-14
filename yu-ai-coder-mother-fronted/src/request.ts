import axios from 'axios';
import { message } from 'ant-design-vue';

// 后端统一响应结构
interface BaseResponse<T = any> {
  code: number;
  data: T;
  message: string;
}

// 后端错误码
const ErrorCode = {
  SUCCESS: 0,
  NOT_LOGIN_ERROR: 40100,
  NOT_AUTH_ERROR: 40101,
};

const request = axios.create({
  baseURL: '/api',
  timeout: 10000,
  withCredentials: true,
});

// 响应拦截器：解包 BaseResponse
request.interceptors.response.use(
  (response) => {
    const res = response.data as BaseResponse;
    if (res.code === ErrorCode.SUCCESS) {
      return res.data as any;
    }
    const errorMsg = res.message || '请求失败';
    if (res.code === ErrorCode.NOT_LOGIN_ERROR) {
      // 已经在登录/注册页面时不跳转，避免无限循环
      if (!window.location.pathname.startsWith('/login') && !window.location.pathname.startsWith('/register')) {
        message.error('请先登录');
        window.location.href = '/login';
      }
    } else if (res.code === ErrorCode.NOT_AUTH_ERROR) {
      message.error('无权限访问');
    } else {
      message.error(errorMsg);
    }
    return Promise.reject(new Error(errorMsg));
  },
  (error) => {
    message.error(error.message || '网络异常');
    return Promise.reject(error);
  }
);

export default request;
