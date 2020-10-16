package com.cy.cblog.service.impl;

import com.cy.cblog.mbg.mapper.LoginLogMapper;
import com.cy.cblog.mbg.model.LoginLog;
import com.cy.cblog.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginLogServiceImpl implements LoginLogService {


    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public int add(LoginLog loginLog) {
        int insert = loginLogMapper.insert(loginLog);
        return insert;
    }

    @Override
    public List<LoginLog> findAll() {
        return null;
    }

    @Override
    public List<LoginLog> findAllByUid(Integer uid) {
        return null;
    }
}
