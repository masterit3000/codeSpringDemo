package com.elearningbackend.utility;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.dto.AnswerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.data.domain.Sort;
import com.elearningbackend.customexception.ElearningException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class ServiceUtils {

    public static boolean checkDataMissing(Object obj, String... fields) throws ElearningException{
        Map<String, List<String>> errorsMap = validateRequired(obj, fields);
        return checkErrorsMap(errorsMap);
    }

    public static Sort proceedSort(String sortBy, String direction) {
        return new Sort(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
    }

    public static String convertListToJson(List list) throws ElearningException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new ElearningException(Errors.CANNOT_CONVERT_LIST_TO_JSON.getId(), Errors.CANNOT_CONVERT_LIST_TO_JSON.getMessage());
        }
    }

    public static List<AnswerDto> convertAnswerDtoJsonToList(String json) throws ElearningException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, AnswerDto.class)
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new ElearningException(Errors.CANNOT_CONVERT_JSON_TO_LIST.getId(), Errors.CANNOT_CONVERT_JSON_TO_LIST.getMessage());
        }
    }

    /**
     * Method set field for Object if this is empty or null
     */
    public static Object convertObject(Object obj,String... fields){
        for (String field: fields) {
            Field foundField = ReflectionUtils.findField(obj.getClass(), field);
            ReflectionUtils.makeAccessible(foundField);
            Object value = ReflectionUtils.getField(foundField, obj);
            if (value == null || (value instanceof String && ((String) value).isEmpty())){
                ReflectionUtils.setField(foundField,obj, Constants.NA);
            }
        }
        return obj;
    }
    private static boolean checkErrorsMap(Map<String, List<String>> errorsMap) throws ElearningException {
        ObjectMapper mapper = new ObjectMapper();
        if (!errorsMap.isEmpty()){
            throw new ElearningException(Errors.ERROR_FIELD_MISS.getId(), errorsMap.toString());
        }
        return true;
    }

    private static Map<String, List<String>> validateRequired(Object obj, String... fields){
        Map<String, List<String>> errorsMap = new HashMap<>();
        List<String> errFields = new ArrayList<>();
        for(String field : fields) {
            Field foundField = ReflectionUtils.findField(obj.getClass(), field);
            ReflectionUtils.makeAccessible(foundField);
            Object value = ReflectionUtils.getField(foundField, obj);
            if (value == null || (value instanceof String && ((String) value).isEmpty())){
                errFields.add(field);
            }
        }
        if (!errFields.isEmpty())
            errorsMap.put(Errors.ERROR_FIELD_MISS.getMessage(), errFields);
        return errorsMap;
    }
}
