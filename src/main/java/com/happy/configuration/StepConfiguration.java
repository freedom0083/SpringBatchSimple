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
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
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

    public StaxEventItemReader xmlReader() {
        StaxEventItemReader xmlReader = new StaxEventItemReader();
        xmlReader.setResource(new ClassPathResource("xml_data.xml"));
        xmlReader.setFragmentRootElementName("info");

        return xmlReader;
    }

    @Bean
    public ItemReader<Response> responseFileReader() {
        logger.info("*************** Reading phase ***************");
        FlatFileItemReader<Response> reader = new FlatFileItemReader<Response>();
        MultiResourceItemReader multiReader = new MultiResourceItemReader();
        //reader.setResource(new ClassPathResource("test-file1.txt"));
        //reader.setResource(new ClassPathResource("file:/resources/test/"));
        multiReader.setResources();

        DefaultLineMapper map = new DefaultLineMapper();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter("|");
        tokenizer.setNames(new String[]{"ID", "contractNbr", "BATCH_ID", "BUSINESS_ID", "RETURN_CODE", "D_FLAG", "DD_FLAG_BATCH", "GC_TYPE", "AMOUNT", "DD_REQ_SN", "DEAL_INFO", "PAYER_BANK_CODE", "PAYER_BANK_REGIN_CODE", "PAYER_BANK_NAME", "PAYER_ACC_TYPE", "PAYER_ACC_COMPANY_TYPE", "PAYER_ACC_NO", "PAYER_ACC_NAME", "PAYER_ID_CARD_TYPE", "PAYER_ID_CARD_NO", "CURRENCY_TYPE", "DD_SIGN", "TRANS_SN", "REMARK", "SYS_REF_CODE", "RESULT_INFO", "SOURCE_ROW_IDX", "ACC_BANK_CODE", "ID_CATEGORY", "PAIRED_TO_DD_REQUEST", "ID_DD_REQ"});

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

        return null;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(6);
        return taskExecutor;
    }
}
