package com.cy.cblog.dto;

import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
public class UserInfo {

    private String username;

    private String avatar;

    private String role;

    private String email;

    private String phone;

}
