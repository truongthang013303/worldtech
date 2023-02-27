package com.example.demo1.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo1.converter.CategoryConverter;
import com.example.demo1.dto.CategoryDTO;
import com.example.demo1.entity.CategoryEntity;
import com.example.demo1.repository.CategoryRepository;
import com.example.demo1.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryConverter categoryConverter; 
	
	@Override
	public List<CategoryDTO> findAll(Pageable pageable) 
	{
		List<CategoryEntity> listCategoryEntity = categoryRepository.findAll(pageable).getContent();
		
		List<CategoryDTO> listCategoryDTO = new ArrayList<>();
		
		for (CategoryEntity categoryEntity : listCategoryEntity) 
		{
			CategoryDTO categoryDto = categoryConverter.toDto(categoryEntity);
			listCategoryDTO.add(categoryDto);
		}
		return listCategoryDTO;
	}
	
	@Override
	public int getTotalItem() 
	{		
		return (int)categoryRepository.count();
	}

	@Override
	public CategoryDTO findById(long id) 
	{	
		CategoryEntity categoryEntity = categoryRepository.findOneById(id);
		CategoryDTO categoryDTO = categoryConverter.toDto(categoryEntity);	
		return categoryDTO;
	}

	@Override
	public CategoryDTO findByCode(String code) 
	{	
		CategoryEntity categoryEntity = categoryRepository.findOneByCode(code);
		CategoryDTO categoryDTO = categoryConverter.toDto(categoryEntity);	
		return categoryDTO;
	}
	
	@Override
	@Transactional
	public CategoryDTO saveOrUpdate(CategoryDTO dto) 
	{
		CategoryEntity categoryEntity = new CategoryEntity();
	
		if(dto.getId()!=null) 
		{
			CategoryEntity oldUser = categoryRepository.findOneById(dto.getId());
			categoryEntity = categoryConverter.toEntity(oldUser,dto);	
		}
		else if(dto.getId()==null && categoryRepository.existsByCode(dto.getCode())==false)//categoryRepository.findExistByCode(dto.getCode())==0
		{
			categoryEntity = categoryConverter.toEntity(dto);	
		}
		else
		{
			System.out.println("Da ton tai");
			return null;
		}
		CategoryEntity categoryEntitySaved = categoryRepository.save(categoryEntity);
		return categoryConverter.toDto(categoryEntitySaved);
	}

	@Override
	@Transactional
	public void delete(long[] ids) 
	{
		for (long id: ids) 
		{
			categoryRepository.deleteById(id);
		}
	}
	
	@Override
	public List<CategoryDTO> findAll() 
	{	
		List<CategoryEntity> listCategoryEntity = categoryRepository.findAll();
		
		List<CategoryDTO> listCategoryDTO = new ArrayList<>();
		
		for (CategoryEntity categoryEntity : listCategoryEntity) 
		{
			CategoryDTO categoryDto = categoryConverter.toDto(categoryEntity);
			listCategoryDTO.add(categoryDto);
		}
		return listCategoryDTO;
	}
}
