package com.happy.reader;

import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResponseFilesReader {
    @Autowired
    private XMLFileReader xmlFileReader;
    @Bean
    public MultiResourceItemReader reader() {
        MultiResourceItemReader reader = new MultiResourceItemReader();
        Resource[] files = new Resource[]{};
        reader.setResources(files);
        reader.setDelegate(xmlFileReader);

        return reader;
    }
}
