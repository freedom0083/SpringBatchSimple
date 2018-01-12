package com.happy.writer;

import com.happy.database.dao.ResponseDao;
import com.happy.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseWriter implements ItemWriter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    @Autowired
    private ResponseDao responseDao;

    @Override
    public void write(List items) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Executing batch with {} items.", items.size() );

        }

        responseDao.insertResponseData(items);
    }

}
