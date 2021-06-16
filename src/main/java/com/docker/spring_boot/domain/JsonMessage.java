package com.docker.spring_boot.domain;

public class JsonMessage {

	private String message;

	public JsonMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
