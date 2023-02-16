package com.java.crm.settings.service;

import com.java.crm.settings.domain.User;

import java.util.Map;

/**
 * @author qqyqq
 * @create 2023-02-15 20:42
 */
public interface UserService {
    User queryUserByLoginActAndPwd(Map<String,Object> map);
}
