package com.happy.configuration;

import com.happy.domain.Response;
import com.happy.processor.SampleProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class StepConfiguration {
    private Logger logger = LoggerFactory.getLogger(StepConfiguration.class);
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    public ItemReader<Response> responseFileReader;

    @Autowired
    public SampleProcessor responseFileProcessor;

    @Autowired
    public JdbcBatchItemWriter<Response> responseFileWriter;

    @Bean
    public Step processResponseStep() {
        return stepBuilderFactory.get("processResponseStep")
                .<Response, Response>chunk(10)
                .reader(responseFileReader)
                .processor(responseFileProcessor)
                .writer(responseFileWriter)
                .taskExecutor(taskExecutor())
                .throttleLimit(4)
                .build();
    }

    @Bean
    public ItemReader<Response> responseFileReader() {
        logger.info("*************** Reading phase ***************");
        FlatFileItemReader<Response> reader = new FlatFileItemReader<Response>();
        reader.setResource(new ClassPathResource("response.txt"));

        DefaultLineMapper map = new DefaultLineMapper();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter("|");
        tokenizer.setNames(new String[]{"firstName", "lastName"});

        map.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper mapper = new BeanWrapperFieldSetMapper();
        mapper.setTargetType(Response.class);
        map.setFieldSetMapper(mapper);

        reader.setLineMapper(map);

        return reader;
    }

    @Bean
    public SampleProcessor responseFileProcessor() {
        logger.info("*************** Processing phase ***************");
        return new SampleProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Response> responseFileWriter() {
        JdbcBatchItemWriter<Response> writer = new JdbcBatchItemWriter<Response>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Response>());
        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(10);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
}
