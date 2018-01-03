package com.happy.configuration;

import com.happy.domain.Response;
import com.happy.listener.JobCompletionNotificationListener;
import com.happy.processor.SampleProcessor;
import com.happy.separatorPolicy.DefaultSeparatorPolicy;
import com.happy.separatorPolicy.JsonCustomSeparatorPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JsonCustomSeparatorPolicy jsonRecordSeparatorPolicy;

    @Autowired
    public DefaultSeparatorPolicy defaultSeparatorPolicy;

    @Bean
    public ItemReader<Response> reader() {
        logger.info("*************** Reading phase ***************");
        FlatFileItemReader<Response> reader = new FlatFileItemReader<Response>();
        reader.setResource(new ClassPathResource("sample-data.csv"));

        DefaultLineMapper map = new DefaultLineMapper();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"firstName", "lastName"});

        map.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper mapper = new BeanWrapperFieldSetMapper();
        mapper.setTargetType(Response.class);
        map.setFieldSetMapper(mapper);

        reader.setLineMapper(map);

        return reader;
    }

    @Bean
    public SampleProcessor processor() {
        logger.info("*************** Processing phase ***************");
        return new SampleProcessor();
    }

    @Bean
    public ItemWriter<Response> writer() {
        logger.info("*************** Writing phase! ***************");

        return null;
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Response, Response>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}
