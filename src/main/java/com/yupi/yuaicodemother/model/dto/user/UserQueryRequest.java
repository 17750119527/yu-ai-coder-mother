package com.yupi.yuaicodemother.model.dto.user;

import com.yupi.yuaicodemother.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;
    // 用户账号
    private String userAccount;
    // 用户角色
    private String userRole;
    //用户名
    private String userName;
    //用户简介
    private String userProfile;

    private static final long serialVersionUID = 1L;
}
