package it.test;

import java.io.Serializable;

public class Result implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean error;
	private String message;
	
	private Result(){}
	
	public static Result build(){
		return new Result();
	}
	public boolean isError() {
		return error;
	}
	public Result setError(boolean error) {
		this.error = error;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public Result setMessage(String message) {
		this.message = message;
		return this;
	}
	
}
