package com.bestbenefits.takoyaki.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomJson {
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static JsonNode convertJsonString2JsonNode(String jsonString) throws JsonProcessingException{
        return objectMapper.readTree(jsonString);
    }
}
