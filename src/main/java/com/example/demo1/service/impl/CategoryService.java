package com.example.demo1.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.demo1.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

	@Autowired
	private Converter<CategoryEntity, CategoryDTO> converter;

	@Override
	public List<CategoryDTO> findAll(Pageable pageable) {
		List<CategoryEntity> listCategoryEntity = categoryRepository.findAll(pageable).getContent();

		List<CategoryDTO> listCategoryDTO = new ArrayList<>();

		for (CategoryEntity categoryEntity : listCategoryEntity) {
//			CategoryDTO categoryDto = categoryConverter.toDto(categoryEntity);
			CategoryDTO categoryDto = converter.toDto(categoryEntity);
			listCategoryDTO.add(categoryDto);
		}
		return listCategoryDTO;
	}

	@Override
	public Long getTotalItem() {
		return categoryRepository.count();
	}

	@Override
	public CategoryDTO findById(long id) {
		CategoryEntity categoryEntity = categoryRepository.findOneById(id);
		CategoryDTO categoryDTO = categoryConverter.toDto(categoryEntity);
		return categoryDTO;
	}

	@Override
	public CategoryDTO findByCode(String code) {
		CategoryEntity categoryEntity = categoryRepository.findOneByCode(code);
		CategoryDTO categoryDTO = categoryConverter.toDto(categoryEntity);
		return categoryDTO;
	}

	@Override
	@Transactional
	public CategoryDTO saveOrUpdate(CategoryDTO dto) {
		CategoryEntity categoryEntity = new CategoryEntity();

		if (dto.getId() != null) {
			CategoryEntity oldUser = categoryRepository.findOneById(dto.getId());
			categoryEntity = categoryConverter.toEntity(oldUser, dto);
		} else if (dto.getId() == null && categoryRepository.existsByCode(dto.getCode()) == false)//categoryRepository.findExistByCode(dto.getCode())==0
		{
			categoryEntity = categoryConverter.toEntity(dto);
		} else {
			System.out.println("Da ton tai");
			return null;
		}
		CategoryEntity categoryEntitySaved = categoryRepository.save(categoryEntity);
		return categoryConverter.toDto(categoryEntitySaved);
	}

	@Override
	@Transactional
	public void delete(long[] ids) {
		for (long id : ids) {
			categoryRepository.deleteById(id);
		}
	}

	@Override
	public List<CategoryDTO> findAll() {
		List<CategoryEntity> listCategoryEntity = categoryRepository.findAll();

		List<CategoryDTO> listCategoryDTO = new ArrayList<>();

		for (CategoryEntity categoryEntity : listCategoryEntity) {
			CategoryDTO categoryDto = categoryConverter.toDto(categoryEntity);
			listCategoryDTO.add(categoryDto);
		}
		return listCategoryDTO;
	}

	@Override
	public List<CategoryDTO> findByNameContaining(String nameCategory) {
		List<CategoryEntity> categories = categoryRepository.findByNameContaining(nameCategory);
		List<CategoryDTO> categoryDTOS = categories.stream().map(c -> categoryConverter.toDto(c)).collect(Collectors.toList());
		return categoryDTOS;
	}

	@Override
	public Page<CategoryDTO> findByPage(Pageable pageable) {
		Page<CategoryEntity> entities = categoryRepository.findAll(pageable);
		Page<CategoryDTO> dtoPage = convertPageEntityToPageDTO(entities);
		return dtoPage;
	}

	public Page<CategoryDTO> convertPageEntityToPageDTO(Page<CategoryEntity> entities)
	{
		Page<CategoryDTO> dtoPage = entities.map(new Function<CategoryEntity, CategoryDTO>() {
			@Override
			public CategoryDTO apply(CategoryEntity entity) {
				CategoryDTO dto = new CategoryDTO();
				dto = categoryConverter.toDto(entity);
				return dto;
			}
		});

		return dtoPage;
	}

}
