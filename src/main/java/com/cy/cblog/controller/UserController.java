package com.cy.cblog.controller;

import com.cy.cblog.common.api.CommonResult;
import com.cy.cblog.common.utils.CreateMenu;
import com.cy.cblog.common.utils.JwtTokenUtil;
import com.cy.cblog.dto.Menu;
import com.cy.cblog.dto.UserInfo;
import com.cy.cblog.mbg.model.Permission;
import com.cy.cblog.mbg.model.Role;
import com.cy.cblog.mbg.model.User;
import com.cy.cblog.service.RoleService;
import com.cy.cblog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "UserController",description = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CreateMenu createMenu;


    @ApiOperation(value = "获取用户信息")
    @GetMapping("/get_user_info")
    public CommonResult<UserInfo> getUserInfo(HttpServletRequest request){
        String usernameByRequest = jwtTokenUtil.getUsernameByRequest(request);
        User user = userService.getUserByUsername(usernameByRequest);
        UserInfo userInfo = new UserInfo();
        userInfo.setAvatar(user.getIcon());
        userInfo.setUsername(usernameByRequest);
        userInfo.setEmail(user.getEmail());
        userInfo.setPhone(user.getPhone());
        Role roleByRid = roleService.getRoleByRid(user.getRoleId());
        userInfo.setRole(roleByRid.getRname());
        return CommonResult.success(userInfo);
    }

    @ApiOperation("获取登录用户所有权限")
    @GetMapping("/permission")
    public CommonResult<List<Menu>> getPermissionList(HttpServletRequest request) {
        try {
            String usernameByRequest = jwtTokenUtil.getUsernameByRequest(request);
            User userByUsername = userService.getUserByUsername(usernameByRequest);
            List<Permission> permissionList = userService.getPermissionList(userByUsername.getUid());
            List<Menu> menus = createMenu.create(permissionList);
            return CommonResult.success(menus);
        }catch (Exception e){
            return CommonResult.failed("获取权限失败");
        }
    }


}
