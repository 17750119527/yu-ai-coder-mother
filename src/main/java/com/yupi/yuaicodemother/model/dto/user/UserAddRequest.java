package com.yupi.yuaicodemother.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddRequest implements Serializable {
    // 用户账号
    private String userAccount;
    // 用户名
    private String userName;
    // 用户密码
    private String userPassword;
    // 用户头像
    private String userAvatar;
    // 用户角色
    private String userRole;
    //用户简介
    private String userProfile;

    private static final long serialVersionUID = 1L;
}
