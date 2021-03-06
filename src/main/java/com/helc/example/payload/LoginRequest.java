package com.helc.example.payload;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class LoginRequest implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String usernameOrEmail;

	@NotBlank
	private String password;

	public LoginRequest() {
		super();
	}

	public LoginRequest(@NotBlank String usernameOrEmail, @NotBlank String password) {
		super();
		this.usernameOrEmail = usernameOrEmail;
		this.password = password;
	}

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
