package com.cy.cblog.controller;

import com.cy.cblog.common.api.CommonResult;
import com.cy.cblog.common.utils.CreateMenu;
import com.cy.cblog.common.utils.IpAndAddrassUtil;
import com.cy.cblog.common.utils.JwtTokenUtil;
import com.cy.cblog.dto.Menu;
import com.cy.cblog.mbg.model.Permission;
import com.cy.cblog.mbg.model.User;
import com.cy.cblog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(tags = "TestController",description = "测试模块")
@RestController
public class TestController {

    @Autowired
    private IpAndAddrassUtil ipUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CreateMenu createMenu;

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    @ApiOperation("测试获取信息")
    public CommonResult<List<Menu>> useragent(HttpServletRequest request){
        String usernameByRequest = jwtTokenUtil.getUsernameByRequest(request);
        User userByUsername = userService.getUserByUsername(usernameByRequest);
        List<Permission> permissionList = userService.getPermissionList(userByUsername.getUid());
        List<Menu> menus = createMenu.create(permissionList);
        return CommonResult.success(menus);
    }
}
