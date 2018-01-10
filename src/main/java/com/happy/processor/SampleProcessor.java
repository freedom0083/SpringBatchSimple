package com.happy.processor;

import com.happy.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.item.ItemProcessor;

public class SampleProcessor implements ItemProcessor<Response, Response> {
    private static Logger logger = LoggerFactory.getLogger(SampleProcessor.class);

    @Override
    public Response process(Response response) throws Exception {
        logger.info("*************** Processing... ***************");
        //String payerAccName = response.getReturnCode().toUpperCase();
        //String payerBankName = response.getResultInfo().toUpperCase();

        Response transformedResponse = new Response();

        logger.info("Converting (" + response + ") into (" + transformedResponse + ")");

        return transformedResponse;
    }

    @AfterProcess
    public void afterProcess() {
        logger.info("*************** Processing end... ***************");
    }

    @BeforeProcess
    public void beforeProcess(Response response) {

        logger.info("*************** Processing start... ***************");

    }

}
