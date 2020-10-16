package com.cy.cblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cy.cblog.common.utils.JwtTokenUtil;
import com.cy.cblog.config.JwtConfig;
import com.cy.cblog.config.RedisConfig;
import com.cy.cblog.dao.UserAndPermission;
import com.cy.cblog.mbg.mapper.RoleMapper;
import com.cy.cblog.mbg.mapper.UserMapper;
import com.cy.cblog.mbg.model.Permission;
import com.cy.cblog.mbg.model.Role;
import com.cy.cblog.mbg.model.User;
import com.cy.cblog.mbg.model.UserExample;
import com.cy.cblog.service.RedisService;
import com.cy.cblog.service.UserService;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private UserAndPermission userAndPermission;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User getUserByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public User register(User login) {
        User user = new User();
        Role role = new Role();
        BeanUtil.copyProperties(login,user);
        user.setCreateTime(new Date());
        user.setStatus(1);
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername());
        List<User> users = userMapper.selectByExample(example);
        if (users.size() > 0){
            return null;
        }
        role.setRname("普通用户");
        userMapper.insert(user);
        return user;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!password.equals(userDetails.getPassword())){
                throw new BadCredentialsException("密码错误");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtTokenUtil.generateToken(userDetails);
            redisService.set(redisConfig.getJwt() + username,token);
            redisService.expire(redisConfig.getJwt() + username,redisConfig.getExpire_jwt());
        }catch (Exception e){
            LOGGER.warn(e.getMessage());
        }
        return token;
    }

    @Override
    public List<Permission> getPermissionList(Integer uid) {
        return userAndPermission.getPermissionList(uid);
    }

    @Override
    public void logout(String username) {
        redisService.remove(redisConfig.getJwt()+ username);
    }
}
