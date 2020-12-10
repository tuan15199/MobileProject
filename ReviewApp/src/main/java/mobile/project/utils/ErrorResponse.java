package mobile.project.utils;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorResponse {
	public static ResponseEntity<Object> getFormattedError(String errorMessage, HttpStatus httpStatus) {
		String errorJsonString = String.format("{\"errorMessage\": \"%s\"}", errorMessage);
		ObjectMapper mapper = new ObjectMapper();
		try {
			return new ResponseEntity<>(mapper.readValue(errorJsonString, Map.class), httpStatus);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Somthing wrong !!!", HttpStatus.BAD_REQUEST);
		}
	}
}
