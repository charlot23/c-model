package com.cy.cblog.service;

import com.cy.cblog.mbg.model.LoginLog;

import java.util.List;

public interface LoginLogService {


    int add(LoginLog loginLog);


    List<LoginLog> findAll();


    List<LoginLog> findAllByUid(Integer uid);
}
