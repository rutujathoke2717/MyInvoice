package com.dev.springboot.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorType {

	public ErrorType(String string, String string2, String message2) {
		// TODO Auto-generated constructor stub
	}
	private String time;
	private String status;
	private String message;
	
}