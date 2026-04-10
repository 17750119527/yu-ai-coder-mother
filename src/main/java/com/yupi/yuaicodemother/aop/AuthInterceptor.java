package com.yupi.yuaicodemother.aop;

import com.yupi.yuaicodemother.annotation.AuthCheck;
import com.yupi.yuaicodemother.entity.User;
import com.yupi.yuaicodemother.exception.BussinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;
import com.yupi.yuaicodemother.model.enums.UserRoleEnum;
import com.yupi.yuaicodemother.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
//在调用带有 @AuthCheck 注解的方法之前，自动拦截并检查用户权限，只有权限满足要求才能执行原方法
public class AuthInterceptor {
    @Resource
    UserService userService;

    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint pointcut, AuthCheck authCheck) throws Throwable {
        //获取要求的权限
        String authRole = authCheck.mustRole();
        UserRoleEnum mustRole = UserRoleEnum.getEnumByValue(authRole);

        //获取当前用户
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
        User user = userService.getLoginUser(request);
        //获取用户角色
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(user.getUserRole());

        //若需权限为空，则直接通过
        if (mustRole ==  null){
            return pointcut.proceed();
        }

        if (userRoleEnum == null){
            throw new BussinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        //要求管理员权限
        if (mustRole.equals(UserRoleEnum.ADMIN) && !userRoleEnum.equals(UserRoleEnum.ADMIN)){
            throw new BussinessException(ErrorCode.NOT_AUTH_ERROR);
        }

        return pointcut.proceed();
    }
}
