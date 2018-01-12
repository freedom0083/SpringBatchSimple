package com.happy.database.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao {
    @Autowired
    @Qualifier("oracleJdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
}
