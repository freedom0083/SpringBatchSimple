package com.happy.configuration;

import com.happy.domain.InputResponse;
import com.happy.domain.Response;
import com.happy.processor.XMLResponseFileProcessor;
import com.happy.writer.ResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableBatchProcessing
@Import({JobConfiguration.class})
public class StepConfiguration {
    private Logger logger = LoggerFactory.getLogger(StepConfiguration.class);

    @Autowired
    public StaxEventItemReader<InputResponse> xmlResponseFileReader;

    @Autowired
    public XMLResponseFileProcessor xmlResponseFileProcessor;

    @Autowired
    public ResponseWriter responseWriter;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step processXMLResponseStep() {
        return stepBuilderFactory.get("processXMLResponseStep1")
                .<InputResponse, List<Response>>chunk(10)
                .reader(xmlResponseFileReader)
                .processor(xmlResponseFileProcessor)
                .writer(xmlFileWriter())
                //.taskExecutor(taskExecutor())
                //.throttleLimit(4)
                .build();
    }


    public ResponseWriter xmlFileWriter() {
        responseWriter.setBatchId(61797168);
        logger.info(" Writing start...");
        return responseWriter;
    }


    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(6);
        return taskExecutor;
    }
}
