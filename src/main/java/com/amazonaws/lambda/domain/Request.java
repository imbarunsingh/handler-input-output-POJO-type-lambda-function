package com.amazonaws.lambda.domain;

public class Request {

	private String firstName;
	private String lastName;

	public Request(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Request() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}