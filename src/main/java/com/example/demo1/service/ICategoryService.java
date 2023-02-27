package com.example.demo1.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.demo1.dto.CategoryDTO;

public interface ICategoryService
{
	List<CategoryDTO> findAll();

	void delete(long[] ids);

	CategoryDTO saveOrUpdate(CategoryDTO dto);

	CategoryDTO findByCode(String code);

	CategoryDTO findById(long id);

	int getTotalItem();

	List<CategoryDTO> findAll(Pageable pageable);
}


