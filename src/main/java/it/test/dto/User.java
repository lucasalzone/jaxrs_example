package it.test.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	private String name;
	private String surname;

	
	public User() {
	}
	
	public User(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + "]";
	}
	
}
