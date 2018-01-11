package com.happy.configuration;

import com.happy.listener.JobCompletionNotificationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {
    private Logger logger = LoggerFactory.getLogger(JobConfiguration.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public Step processXMLResponseStep;

    @Bean
    public Job processResponseFileJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("processResponseFileJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(processXMLResponseStep)
                .end()
                .build();
    }

}
