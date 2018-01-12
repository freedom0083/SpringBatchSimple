package com.happy.configuration;

import com.happy.database.dao.ResponseDao;
import com.happy.domain.InputResponse;
import com.happy.domain.Response;
import com.happy.processor.XMLResponseFileProcessor;
import com.happy.writer.ResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class StepConfiguration {
    private Logger logger = LoggerFactory.getLogger(StepConfiguration.class);

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public StaxEventItemReader<InputResponse> xmlResponseFileReader;

    @Autowired
    public XMLResponseFileProcessor xmlResponseFileProcessor;

    @Autowired
    public ItemWriter responseWriter;
    @Autowired
    private ResponseDao responseDao;

    //@Autowired
    //public ItemWriter<InputResponse> xmlFileWriter;

    @Bean
    public Step processXMLResponseStep() {
        return stepBuilderFactory.get("processXMLResponseStep")
                .<InputResponse, List<Response>>chunk(10)
                .reader(xmlResponseFileReader)
                .processor(xmlResponseFileProcessor)
                .writer(xmlFileWriter())
                //.taskExecutor(taskExecutor())
                //.throttleLimit(4)
                .build();
    }


    public ItemWriter<List<Response>> xmlFileWriter() {
        //responseDao.insertResponseData(items);
        logger.info(" Writing start...");
        return null;
    }


    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(6);
        return taskExecutor;
    }
}
