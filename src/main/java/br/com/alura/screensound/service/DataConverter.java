package br.com.alura.screensound.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IDataConverter{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T convertTo(String json, Class<T> convertToClass) {
        try {
            return mapper.readValue(json, convertToClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
