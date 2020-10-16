package com.cy.cblog.service;

import com.cy.cblog.mbg.model.Permission;
import com.cy.cblog.mbg.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 根据用户名获取后台管理员
     */
    User getUserByUsername(String username);

    /**
     * 注册功能
     */
    User register(User user);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<Permission> getPermissionList(Integer uid);


    void logout(String username);



}
