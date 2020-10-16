package com.cy.cblog.controller;

import com.cy.cblog.common.api.CommonResult;
import com.cy.cblog.common.api.ResultCode;
import com.cy.cblog.common.utils.IpAndAddrassUtil;
import com.cy.cblog.common.utils.JwtTokenUtil;
import com.cy.cblog.config.JwtConfig;
import com.cy.cblog.config.RedisConfig;
import com.cy.cblog.dto.UserLoginParam;
import com.cy.cblog.mbg.model.LoginLog;
import com.cy.cblog.mbg.model.Permission;
import com.cy.cblog.mbg.model.User;
import com.cy.cblog.mbg.model.UserExample;
import com.cy.cblog.service.LoginLogService;
import com.cy.cblog.service.UserService;
import com.cy.cblog.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nl.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "LoginController",description = "登录管理")
@RestController
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private IpAndAddrassUtil ipAndAddrassUtil;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    @ResponseBody
    public CommonResult<User> register(@RequestBody User user) {
        User reg = userService.register(user);
        if (reg == null) {
            CommonResult.failed("用户已存在");
        }
        return CommonResult.success(reg);
    }

    @ApiOperation(value = "登录以后返回token,并存入redis中")
    @PostMapping("/login")
    @ResponseBody
    public CommonResult<Map<String,String>> login(@RequestBody UserLoginParam userLoginParam, HttpServletRequest request) {
        String token = userService.login(userLoginParam.getUsername(), userLoginParam.getPassword());

        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }

        LoginLog loginLog = ipAndAddrassUtil.setLoginLog(request, userService.getUserByUsername(userLoginParam.getUsername()));

        int add = loginLogService.add(loginLog);

        if (add == 0){
            LOGGER.error("登录日志写入失败");
            return CommonResult.failed("服务器错误请联系管理");
        }

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", jwtConfig.getTokenHead()+" "+token);

        return CommonResult.success(tokenMap);
    }



    @ApiOperation("登出")
    @PostMapping("/logout")
    public CommonResult<String> logout(HttpServletRequest request){
        try {
            String usernameByRequest = jwtTokenUtil.getUsernameByRequest(request);
            userService.logout(usernameByRequest);
            return CommonResult.success("退出成功");
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return CommonResult.failed(500,e.getMessage());
        }
    }

}
