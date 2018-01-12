package com.happy.reader;


import com.happy.domain.InputResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

@Component
public class XMLFileReader<T> extends StaxEventItemReader<T> {
    private static final Logger logger = LoggerFactory.getLogger(XMLFileReader.class);
    private static final String ROOT_ELEMENT_NAME = "detail_info";

    @Override
    public void afterPropertiesSet() throws Exception {
        setResource(new ClassPathResource("xml_data.xml"));
        setFragmentRootElementName(ROOT_ELEMENT_NAME);

        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setClassesToBeBound(InputResponse.class);
        setUnmarshaller(unmarshaller);

        super.afterPropertiesSet();
    }

}
