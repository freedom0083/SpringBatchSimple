package com.happy.database;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.SQLException;

@Component
public class ResponseCallableStatementCallback<T> implements CallableStatementCallback<java.lang.Integer> {
    @Override
    public Integer doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
        int returnValue = -1;

        cs.execute();

        returnValue = cs.getInt(2);

        return returnValue;
    }
}
