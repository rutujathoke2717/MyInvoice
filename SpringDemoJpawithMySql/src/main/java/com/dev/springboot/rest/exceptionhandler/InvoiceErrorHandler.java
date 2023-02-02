package com.dev.springboot.rest.exceptionhandler;

import org.springframework.http.HttpStatus;
import java.util.Date;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dev.springboot.rest.entity.ErrorType;
import com.dev.springboot.rest.exception.InvoiceNotFoundException;

@RestControllerAdvice
public class InvoiceErrorHandler {

	
	@ExceptionHandler(InvoiceNotFoundException.class)
	public ResponseEntity<ErrorType> handleNotFound(InvoiceNotFoundException nfe){
		
		return new ResponseEntity<ErrorType>
		(new ErrorType
				(new Date
						(System.currentTimeMillis()).toString(),
						"404- NOT FOUND", 
						nfe.getMessage()), 
				HttpStatus.NOT_FOUND);
	}
}
