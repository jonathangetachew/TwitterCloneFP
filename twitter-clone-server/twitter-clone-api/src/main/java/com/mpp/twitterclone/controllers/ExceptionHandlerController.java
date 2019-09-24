package com.mpp.twitterclone.controllers;

import com.mpp.twitterclone.exceptions.ResourceExistsException;
import com.mpp.twitterclone.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan on 9/13/2019.
 */

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ResourceNotFoundException.class })
	public ResponseEntity<?> resourceNotFoundHandler(ResourceNotFoundException ex) {
		Map<Object, Object> response = new HashMap<>();
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
		response.put("message", ex.getMessage());

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
	}

	@ExceptionHandler({ ResourceExistsException.class })
	public ResponseEntity<?> resourceExistsHandler(ResourceExistsException ex) {
		Map<Object, Object> response = new HashMap<>();
		response.put("status", HttpStatus.CONFLICT.value());
		response.put("error", HttpStatus.CONFLICT.getReasonPhrase());
		response.put("message", ex.getMessage());

		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(response);
	}
}
