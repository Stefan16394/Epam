package com.vmzone.demo.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vmzone.demo.exceptions.VMZoneException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(VMZoneException.class)
	public void handleException(final VMZoneException e) throws VMZoneException {
		throw e;
	}
	
}
