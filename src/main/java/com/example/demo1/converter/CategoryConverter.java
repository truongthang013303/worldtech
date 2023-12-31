package com.example.demo1.converter;

import org.springframework.stereotype.Component;

import com.example.demo1.dto.CategoryDTO;
import com.example.demo1.entity.CategoryEntity;

@Component
public class CategoryConverter implements Converter<CategoryEntity, CategoryDTO>{
	
	public CategoryDTO toDto(CategoryEntity entity) {
		CategoryDTO result = new CategoryDTO();
		result.setId(entity.getId());
		result.setCode(entity.getCode());
		result.setName(entity.getName());
		//Neu nhu cate vua moi dc tao ra bai viet thuoc cate=0 goi .getNews() se tra ve nulll=>NullPointerException
		if(entity.getNews()==null){
			result.setPostCount(0);
		}else{
			result.setPostCount(entity.getNews().size());
		}
		result.setCreatedBy(entity.getCreatedBy());
		result.setCreatedDate(entity.getModifiedDate());
		return result;
	}
	
	public CategoryEntity toEntity(CategoryDTO dto) {
		CategoryEntity result = new CategoryEntity();
		result.setCode(dto.getCode());
		result.setName(dto.getName());
		return result;
	}
	
	public CategoryEntity toEntity(CategoryEntity cateEntity, CategoryDTO categoryDTO) 
	{
		cateEntity.setName(categoryDTO.getName());
		cateEntity.setCode(categoryDTO.getCode());		
		return cateEntity;
	}
}
