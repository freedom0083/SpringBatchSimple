package com.happy.reader;

import com.happy.domain.Response;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class JsonReader implements ItemReader<Response> {
    @Override
    public Response read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
