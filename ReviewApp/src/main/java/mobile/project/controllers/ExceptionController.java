package mobile.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import mobile.project.exceptions.CustomException;
import mobile.project.exceptions.DataAlreadyExistException;
import mobile.project.exceptions.DataNotFoundException;
import mobile.project.exceptions.UnauthorizationException;
import mobile.project.utils.ErrorResponse;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(value = CustomException.class)
	public ResponseEntity<Object> exception(CustomException exception) {
		return ErrorResponse.getFormattedError("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(value = DataAlreadyExistException.class)
	public ResponseEntity<Object> exception(DataAlreadyExistException exception) {
		return ErrorResponse.getFormattedError(exception.errorField + " is already exist", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = DataNotFoundException.class)
	public ResponseEntity<Object> exception(DataNotFoundException exception) {
		return ErrorResponse.getFormattedError(exception.errorField + " not found", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = UnauthorizationException.class)
	public ResponseEntity<Object> exception(UnauthorizationException exception) {
		return ErrorResponse.getFormattedError(exception.errorField, HttpStatus.UNAUTHORIZED);
	}

}
