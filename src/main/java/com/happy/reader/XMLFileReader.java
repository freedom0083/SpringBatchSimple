package com.happy.reader;


import com.happy.domain.InputResponse;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

@Component
public class XMLFileReader extends StaxEventItemReader {
    @Override
    public StaxEventItemReader read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        StaxEventItemReader xmlReader = new StaxEventItemReader();
        xmlReader.setResource(new ClassPathResource("xml_data.xml"));
        xmlReader.setFragmentRootElementName("detail_info");

        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setClassesToBeBound(InputResponse.class);
        xmlReader.setUnmarshaller(unmarshaller);

        return xmlReader;
    }
}
