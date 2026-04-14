import request from '@/request';

// ===== 类型定义（与后端对齐） =====

/** 登录用户视图对象 */
export interface LoginUserVO {
  id: number;
  userAccount: string;
  userName: string;
  userAvatar: string;
  userRole: string;
  userProfile: string;
  createTime: string;
}

/** 用户视图对象（管理员查询） */
export interface UserVO {
  id: number;
  userAccount: string;
  userName: string;
  userAvatar: string;
  userRole: string;
  userProfile: string;
  createTime: string;
}

/** 用户注册请求 */
export interface UserRegisterRequest {
  userAccount: string;
  userPassword: string;
  checkPassword: string;
}

/** 用户登录请求 */
export interface UserLoginRequest {
  userAccount: string;
  userPassword: string;
}

/** 新增用户请求（管理员） */
export interface UserAddRequest {
  userAccount?: string;
  userName?: string;
  userPassword?: string;
  userAvatar?: string;
  userRole?: string;
  userProfile?: string;
}

/** 更新用户请求（管理员） */
export interface UserUpdateRequest {
  id: number;
  userAccount?: string;
  userName?: string;
  userPassword?: string;
  userRole?: string;
  userProfile?: string;
}

/** 用户查询请求（管理员分页） */
export interface UserQueryRequest {
  pageNum: number;
  pageSize: number;
  sortFiled?: string;
  sortOrder?: string;
  id?: number;
  userAccount?: string;
  userName?: string;
  userRole?: string;
  userProfile?: string;
}

/** 分页结果 */
export interface PageResult<T> {
  current: number;
  pages: number;
  records: T[];
  size: number;
  total: number;
}

// ===== API 函数 =====

/** 用户注册 */
export function userRegister(data: UserRegisterRequest) {
  return request.post<any, number>('/user/register', data);
}

/** 用户登录 */
export function userLogin(data: UserLoginRequest) {
  return request.post<any, LoginUserVO>('/user/login', data);
}

/** 获取当前登录用户 */
export function getLoginUser() {
  return request.get<any, LoginUserVO>('/user/get/login');
}

/** 用户注销 */
export function userLogout() {
  return request.post<any, boolean>('/user/logout');
}

/** 新增用户（管理员） */
export function addUser(data: UserAddRequest) {
  return request.post<any, number>('/user/add', data);
}

/** 删除用户（管理员） */
export function deleteUser(id: number) {
  return request.post<any, boolean>('/user/delete', { id });
}

/** 更新用户（管理员） */
export function updateUser(data: UserUpdateRequest) {
  return request.post<any, boolean>('/user/update', data);
}

/** 分页查询用户（管理员） */
export function listUserByPage(data: UserQueryRequest) {
  return request.post<any, PageResult<UserVO>>('/user/list/page/vo', data);
}
