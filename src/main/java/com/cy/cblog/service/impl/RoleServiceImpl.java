package com.cy.cblog.service.impl;

import com.cy.cblog.dao.UserAndRole;
import com.cy.cblog.mbg.model.Role;
import com.cy.cblog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserAndRole userAndRole;


    @Override
    public Role getRoleByRid(Integer roleId) {
        return userAndRole.getRoleByRid(roleId);
    }

}
