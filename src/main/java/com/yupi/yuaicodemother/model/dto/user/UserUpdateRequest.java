package com.yupi.yuaicodemother.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {
    private Long id;
    // 用户账号
    private String userAccount;
    //用户名
    private String userName;
    // 密码
    private String userPassword;
    // 用户角色
    private String userRole;
    //用户简介
    private String userProfile;

    private static final long serialVersionUID = 1L;
}
