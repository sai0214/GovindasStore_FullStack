package com.eCommerce.GovindasStore.Exception;

import lombok.Data;

@Data
public class ErrorResponse {

	private int status;
    private String message;
    private long timestamp;
    
	public ErrorResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp=System.currentTimeMillis();
	}
    
    
}
