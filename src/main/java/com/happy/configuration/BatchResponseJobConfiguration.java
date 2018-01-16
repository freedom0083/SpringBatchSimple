package com.happy.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchResponseJobConfiguration {
    @Autowired
    public Step processXMLResponseStep;

    @Bean
    Job processResponseFileJob(JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("processResponseFileJob")
                .incrementer(new RunIdIncrementer())
                .flow(processXMLResponseStep)
                .end()
                .build();
    }
}
