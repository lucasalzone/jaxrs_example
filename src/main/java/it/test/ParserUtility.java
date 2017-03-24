package it.test;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParserUtility {
	public static <T> T parseString(String jsonString, Class<T> classType) throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		T userClass = mapper.readValue(jsonString, classType);
		return userClass;
	}
	
	public static <T> String parseObject(T object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(object);
		return result.toString();
	}
}
