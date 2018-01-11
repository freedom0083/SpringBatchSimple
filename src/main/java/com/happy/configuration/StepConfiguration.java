package com.happy.configuration;

import com.happy.domain.InputResponse;
import com.happy.domain.Response;
import com.happy.processor.XMLResponseFileProcessor;
import com.happy.reader.XMLFileReader;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class StepConfiguration {
    private Logger logger = LoggerFactory.getLogger(StepConfiguration.class);
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    public XMLFileReader xmlFileReader;

    @Autowired
    public XMLResponseFileProcessor xmlFileProcessor;

    //@Autowired
    //public ItemWriter<InputResponse> xmlFileWriter;

    @Bean
    public Step processXMLResponseStep() {
        return stepBuilderFactory.get("processXMLResponseStep")
                .<InputResponse, List<Response>>chunk(10)
                .reader(xmlFileReader)
                .processor(xmlFileProcessor)
                .writer(xmlFileWriter())
                //.taskExecutor(taskExecutor())
                //.throttleLimit(4)
                .build();
    }

    /*
    @Bean
    public ItemReader<InputResponse> xmlFileReader() {

        StaxEventItemReader xmlReader = new StaxEventItemReader();
        xmlReader.setResource(new ClassPathResource("xml_data.xml"));
        xmlReader.setFragmentRootElementName("detail_info");

        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setClassesToBeBound(InputResponse.class);
        xmlReader.setUnmarshaller(unmarshaller);

        return xmlReader;
    }*/


    public ItemWriter<List<Response>> xmlFileWriter() {
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
