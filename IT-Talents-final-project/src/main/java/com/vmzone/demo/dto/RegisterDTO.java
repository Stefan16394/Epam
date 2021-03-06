package com.vmzone.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class RegisterDTO {
	@NotBlank(message="first name should not be empty")
	private String firstName;
	@NotBlank(message ="last name should not be empty")
	private String lastName;
	@Email
    private String email;
    @NotBlank(message = "password should be at least 4 characters")
    @Size(min = 4, max = 50, message="password should be at least 4 characters")
	private String password;
    
    @Pattern(regexp = "^Male|Female$",message = "Gender should be Male or Female")
	private String gender;
	//TODO should not be here
	
	@NotNull(message = "is subcribed should have value 0(not subscribed) or 1(subscribed)")
	@Min(0)
	@Max(1)
	private int isSubscribed;
}
