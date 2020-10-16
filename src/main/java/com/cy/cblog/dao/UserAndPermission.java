package com.cy.cblog.dao;

import com.cy.cblog.mbg.model.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAndPermission {

    List<Permission> getPermissionList(@Param("uid")Integer uid);
}
