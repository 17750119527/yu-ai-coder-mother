package com.yupi.yuaicodemother.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {
    //用户id
    private Long id;
    //用户账号
    private String userAccount;
    //用户名称
    private String userName;
    //用户头像
    private String userAvatar;
    //用户角色 admin/user
    private String userRole;
    //用户简介
    private String userProfile;
    //创建时间
    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}
