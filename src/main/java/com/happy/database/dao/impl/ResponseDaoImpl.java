package com.happy.database.dao.impl;

import com.happy.database.ResponseCallableStatementCallback;
import com.happy.database.ResponseCallableStatmentCreator;
import com.happy.database.dao.ResponseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResponseDaoImpl extends BaseDao implements ResponseDao {
    @Autowired
    private ResponseCallableStatmentCreator callableStatmentCreator;

    @Autowired
    private ResponseCallableStatementCallback<Integer> callableStatementCallback;

    @Override
    public int insertResponseData(List responseList) {
        callableStatmentCreator.setResponseList(responseList);

        return jdbcTemplate.execute(callableStatmentCreator, callableStatementCallback);
    }

}
