package com.happy.writer;

import com.happy.database.mapper.ProcedureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResponseWriter implements ItemWriter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);
    private long batchId;

    @Resource
    private ProcedureMapper procedureMapper;

    @Override
    public void write(List items) throws Exception {
        Map paramMap = new HashMap();
        int returnValue = 0;
        if (logger.isDebugEnabled()) {
            logger.debug("Executing batch {} with {} items.", batchId, items.size() );
        }

        paramMap.put("batchId", batchId);
        paramMap.put("list", items.get(0));

        if (items != null && items.size() > 0) {
            procedureMapper.insertResponseAndUpdateBatchWithProcedure(paramMap);
            returnValue = (Integer) paramMap.get("poFlag");
        }

        logger.info("{} response details with batch ID {} were inserted", returnValue);

        if (returnValue == -1) {
            logger.error("Records in response file with batch ID {} not match request file!", batchId);
        }

        return;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }
}
