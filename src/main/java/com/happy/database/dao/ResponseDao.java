package com.happy.database.dao;

import java.util.List;

public interface ResponseDao<T> {
    public int insertResponseData(List<? extends T> responseList);
}
