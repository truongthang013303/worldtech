package com.example.demo1.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class NewDTO extends AbstractDTO<NewDTO> {
	
	@NotBlank
	@Length(max = 95)
	private String title;
	
	@NotBlank
	private String thumbnail;
	//private String base64;
	
	@NotBlank
	private String name;
	
	@NotBlank
	@Length(max = 190)
	private String shortDescription;
	
	@NotBlank
	private String content;
		
	@NotBlank
	private String categoryCode;
	/*
	 * public String getBase64() { return base64; } public void setBase64(String
	 * base64) { this.base64 = base64; }
	 */
	public NewDTO() 
	{
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
}
