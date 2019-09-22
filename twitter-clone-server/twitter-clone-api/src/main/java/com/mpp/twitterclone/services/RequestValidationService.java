package com.mpp.twitterclone.services;

import com.mpp.twitterclone.exceptions.UnauthorizedUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan on 9/14/2019.
 */

@Service
public class RequestValidationService {
	public ResponseEntity<?> validate(BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<Object, Object> response = new HashMap<>();
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());

			Map<Object, Object> message = new HashMap<>();

			bindingResult.getFieldErrors().forEach(fieldError -> message.put(fieldError.getField(),
					fieldError.getDefaultMessage()));

			response.put("message", message);

			return ResponseEntity
					.badRequest()
					.body(response);

		}

		return null;

	}

	public void validateUser(Principal principal) {

		try {
			principal.getName();
		} catch (Exception ex) {
			throw new UnauthorizedUserException("User Token Authentication Failed");
		}

	}
}
