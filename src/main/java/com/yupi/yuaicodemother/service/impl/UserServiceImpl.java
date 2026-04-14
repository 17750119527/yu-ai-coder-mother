package com.yupi.yuaicodemother.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.yupi.yuaicodemother.annotation.AuthCheck;
import com.yupi.yuaicodemother.common.BaseResponse;
import com.yupi.yuaicodemother.common.ResultUtils;
import com.yupi.yuaicodemother.constant.UserConstant;
import com.yupi.yuaicodemother.entity.User;
import com.yupi.yuaicodemother.Mapper.UserMapper;
import com.yupi.yuaicodemother.exception.BussinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;
import com.yupi.yuaicodemother.exception.ThrowUtils;
import com.yupi.yuaicodemother.model.dto.user.UserAddRequest;
import com.yupi.yuaicodemother.model.dto.user.UserQueryRequest;
import com.yupi.yuaicodemother.model.enums.UserRoleEnum;
import com.yupi.yuaicodemother.model.vo.LoginUserVO;
import com.yupi.yuaicodemother.model.vo.UserVO;
import com.yupi.yuaicodemother.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static cn.hutool.core.lang.Console.log;
import static com.yupi.yuaicodemother.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户 服务层实现。
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService {
    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword){
        // 1. 校验
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword)){
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if (userAccount.length()<4){
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }
        if (userPassword.length()<5 || checkPassword.length()<5){
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }
        if (!userPassword.equals(checkPassword)){
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"两次输入的密码不一致");
        }
//        2.检查是否重复
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount",userAccount);
        long count = this.mapper.selectCountByQuery(queryWrapper);
        if (count>0){
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"账号重复");
        }
//        3.加密密码
        String encryptedPassword = getEncryptedPassword(userPassword);
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        user.setUserName("未设置");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean save = this.save(user);
        if (!save){
            throw new BussinessException(ErrorCode.OPERATION_ERROR,"数据库错误，注册失败");
        }
        return user.getId();
    }

    /*
    * 获取脱敏用户信息
    * @param user
    * @return LoginUserVO
    * */
    @Override
    public LoginUserVO getUserLoginVO(User user){
        if (user == null){
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user,loginUserVO);
        return loginUserVO;
    }

    /*
    * 用户登录
    * @param userAccount
    * @param userPassword
    * @param request
    * @return LoginUserVO
    * */
    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验参数
        if (StrUtil.hasBlank(userAccount, userPassword)) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "账号长度过短");
        }
        if (userPassword.length() < 6) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "密码长度过短");
        }
        // 2. 加密
        String encryptPassword = getEncryptedPassword(userPassword);
        // 3. 查询用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.mapper.selectOneByQuery(queryWrapper);

        if (user == null) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 4. 如果用户存在，记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        // 5. 返回脱敏的用户信息
        return this.getUserLoginVO(user);
    }

    /*
    * 获取用户的登录信息
    * */

    @Override
    public User getLoginUser(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) attribute;
        if (user == null || user.getId() == null){
            throw new BussinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Long userId = user.getId();
        User loginUser = this.getById(userId);
        if (loginUser == null){
            throw new BussinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return loginUser;
    }

    /*
    * 用户注销
    * @param request
    * @return true
    * */
    @Override
    public Boolean getUserLogout(HttpServletRequest request){
        Object object = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (object == null){
            throw new BussinessException(ErrorCode.OPERATION_ERROR,"未登录");
        }
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }


    @Override
    public UserVO getUserVO(User user) {
        if (user == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if (CollUtil.isEmpty(userList)){
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).toList();
    }


    @Override
    public QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null){
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = userQueryRequest.getId();
        String userAccount = userQueryRequest.getUserAccount();
        String userName = userQueryRequest.getUserName();
        String userRole = userQueryRequest.getUserRole();
        String userProfile = userQueryRequest.getUserProfile();
        String sortField = userQueryRequest.getSortFiled();
        String sortOrder = userQueryRequest.getSortOrder();
        return QueryWrapper.create()
                .eq("id",id)
                .like("userAccount",userAccount)
                .like("userName",userName)
                .eq("userRole",userRole)
                .like("userProfile",userProfile)
                .orderBy(sortField, "ascend".equals(sortOrder));

    }

    public String getEncryptedPassword(String userPassword){
        final String SALT = "yupi";
        return DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes(StandardCharsets.UTF_8));
    }

}
