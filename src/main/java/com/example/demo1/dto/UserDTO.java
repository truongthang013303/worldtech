package com.example.demo1.dto;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.demo1.entity.PostRating;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.config.SetFactoryBean;

@Setter
@Getter
public class UserDTO extends AbstractDTO<UserDTO>
{	
	@NotBlank
	@NotNull
	@Size(min = 1, max = 32, message = "userName must be between 1 and 32 characters")
	private String userName;

	@NotBlank
	@NotNull
	@Size(min = 1, max = 32, message = "password must be between 1 and 32 characters")
	private String password;
	
	@NotBlank
	@NotNull
	@Size(min = 1, max = 200, message = "fullName must be between 1 and 200 characters")
	private String fullName;
	
	private Integer status;

	private String interestCode;

	private List<String> roleCode = new ArrayList<>();

	private Set<PostRating> ratings;
}
