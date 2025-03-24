package br.com.alurafood.order.order.config.mapper;

import br.com.alurafood.order.order.dto.PaymentQueueDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    public static <T> T convertTo (String message, Class<T> classDto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.readValue(message,  classDto);
    }

//    public <T> T readValue(String content, Class<T> valueType) throws JsonProcessingException, JsonMappingException {
//        this._assertNotNull("content", content);
//        return this.readValue(content, this._typeFactory.constructType(valueType));
//    }
}
