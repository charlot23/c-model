package com.cy.cblog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.cy.cblog.mbg.mapper","com.cy.cblog.dao"})
public class MybatisConfig {
}
