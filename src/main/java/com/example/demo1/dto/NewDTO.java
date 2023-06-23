package com.example.demo1.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.demo1.entity.PostRating;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Map;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewDTO extends AbstractDTO<NewDTO> {

//	private Integer status;
	//By default, Jackson will represent Java Enums as a simple String like CHODUYET, DADUYET...
	private PostStatus status;
	//Serialize to JSON when return by API: {...,postStatusClass: { postStatusCode: 0, postStatusName: "PostStatusClass-CHODUYET" },...}
	//private PostStatusClass postStatusClass = new PostStatusClass(0);
	@NotBlank
	@NotNull
	@Size(min = 10, max = 200, message
			= "code must be between 10 and 200 characters")
	private String title;
	
//	@NotBlank
	private String thumbnail;
	//private String base64;
	
//	@NotBlank
	private String name;
	
	@NotBlank
	@NotNull
	@Size(min = 10, max = 200, message
			= "code must be between 10 and 200 characters")
	private String shortDescription;
	
	@NotBlank
	@NotNull
	private String content;
		
	@NotBlank
	private String categoryCode;

	private CategoryDTO category;

	private String messageDenied;
	/*
	 * public String getBase64() { return base64; } public void setBase64(String
	 * base64) { this.base64 = base64; }
	 */
	private Set<PostRating> ratings;
/*	public PostStatusClass getPostStatusClass() {
		return postStatusClass;
	}

	public void setPostStatusClass(PostStatusClass postStatusClass) {
		this.postStatusClass = postStatusClass;
	}*/
}
