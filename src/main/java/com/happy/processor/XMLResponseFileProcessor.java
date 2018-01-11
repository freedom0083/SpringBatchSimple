package com.happy.processor;

import com.happy.domain.InputResponse;
import com.happy.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class XMLResponseFileProcessor implements ItemProcessor<InputResponse, List<Response>> {
    private static final Logger logger = LoggerFactory.getLogger(XMLResponseFileProcessor.class);
    @Override
    public List<Response> process(InputResponse item) throws Exception {

        logger.info("***************** XML parse starting *****************");
        logger.info("Information in XLM is: {} ", item.getInfoList().toString());
        List<Response> responseList = null;
        List<String> infoList = item.getInfoList();
        Timestamp time = null;

        if (infoList != null && infoList.size() != 0) {
            responseList = new ArrayList<>();
            time = new Timestamp(new Date().getTime());

            for (String s : infoList) {
                Response response = new Response();

                String[] splitArray = s.split("\\|");

                if (splitArray.length == 3) {
                    response.setId(Long.valueOf(splitArray[0]));
                    response.setReturnCode(splitArray[1]);
                    response.setResultInfo(splitArray[2]);
                    response.setInsertTime(time);

                    logger.info("Transaction ID is: {}.", response.getId());
                    logger.info("Return code is: {}.", response.getReturnCode());
                    logger.info("Return message is: {}.", response.getResultInfo());
                    logger.info("Insert time is: {}.", response.getInsertTime());

                }

                responseList.add(response);
            }
        }

        logger.info("***************** XML parse completed *****************");

        return responseList;
    }
}
