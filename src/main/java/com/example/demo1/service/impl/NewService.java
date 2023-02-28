package com.example.demo1.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo1.converter.NewConverter;
import com.example.demo1.dto.NewDTO;
import com.example.demo1.entity.CategoryEntity;
import com.example.demo1.entity.NewEntity;
import com.example.demo1.repository.CategoryRepository;
import com.example.demo1.repository.NewRepository;
import com.example.demo1.service.INewService;
import com.example.demo1.utils.UpLoadFileUtils;


@Service
public class NewService implements INewService {
	
	@Autowired
	private NewRepository newRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private NewConverter newConverter;
	@Autowired
	private UpLoadFileUtils upLoadFileUtils;
	
	@Override
	public List<NewDTO> findAll(Pageable pageable) 
	{
		List<NewDTO> models = new ArrayList<>();
		List<NewEntity> entities = newRepository.findAll(pageable).getContent();
		for (NewEntity item: entities) {
			NewDTO newDTO = newConverter.toDto(item);
			models.add(newDTO);
		}
		return models;
	}

	@Override
	public Page<NewDTO> findByPage(Pageable pageable) {
		Page<NewEntity> entities = newRepository.findAll(pageable);

		Page<NewDTO> dtoPage = entities.map(new Function<NewEntity, NewDTO>() {
			@Override
			public NewDTO apply(NewEntity entity) {
				NewDTO dto = new NewDTO();
				dto.setId(entity.getId());
				dto.setTitle(entity.getTitle());
				dto.setShortDescription(entity.getShortDescription());
				dto.setThumbnail(entity.getThumbnail());
				dto.setContent(entity.getContent());
				dto.setCreatedDate(entity.getCreatedDate());
				dto.setCreatedBy(entity.getCreatedBy());
				dto.setModifiedDate(entity.getModifiedDate());
				dto.setModifiedBy(entity.getModifiedBy());
				dto.setCategoryCode(entity.getCategory().getCode());
				return dto;
			}
		});

		return dtoPage;
	}

	@Override
	public int getTotalItem() {
		return (int) newRepository.count();
	}
	
	@Override
	public int getTotalItemByCategory_id(long categoryId) 
	{
		return (int) newRepository.countByCategory_id(categoryId);
	}

	@Override
	public NewDTO findById(long id) {
		NewEntity newEntity = newRepository.findOneById(id);
		NewDTO newDto = newConverter.toDto(newEntity);// convert từ newEntity sang dạng data DTO
		return newDto;
	}
	
	@Override
	public List<NewDTO> findAllByCategoryCode(String cateCode, Pageable pageable)
	{
		Page<NewEntity> pageNewEntity = newRepository.findAllByCategoryCode(cateCode, pageable);
		List<NewDTO> listNewDTO = new ArrayList<>();
		for (NewEntity newEntity : pageNewEntity) {
			listNewDTO.add(newConverter.toDto(newEntity));
		}
		return listNewDTO;
	}

	@Override
	public Page<NewDTO> findAllByCategoryCodePage(String cateCode, Pageable pageable)
	{
		Page<NewEntity> entities = newRepository.findAllByCategoryCode(cateCode, pageable);

		Page<NewDTO> dtoPage = entities.map(new Function<NewEntity, NewDTO>() {
			@Override
			public NewDTO apply(NewEntity entity) {
				NewDTO dto = new NewDTO();
				dto.setId(entity.getId());
				dto.setTitle(entity.getTitle());
				dto.setShortDescription(entity.getShortDescription());
				dto.setThumbnail(entity.getThumbnail());
				dto.setContent(entity.getContent());
				dto.setCreatedDate(entity.getCreatedDate());
				dto.setCreatedBy(entity.getCreatedBy());
				dto.setModifiedDate(entity.getModifiedDate());
				dto.setModifiedBy(entity.getModifiedBy());
				dto.setCategoryCode(entity.getCategory().getCode());
				return dto;
			}
		});
		return dtoPage;
	}
	
	@Override
	@Transactional
	public NewDTO saveOrUpdate(NewDTO dto) 
	{
		CategoryEntity category = categoryRepository.findOneByCode(dto.getCategoryCode());

		NewEntity newEntity = new NewEntity();  
		if (dto.getId() != null) {
			NewEntity oldNewEnity = newRepository.findOneById(dto.getId());
			oldNewEnity.setCategory(category);
			newEntity = newConverter.toEntity(oldNewEnity, dto);
		} else if(newRepository.existsByTitle(dto.getTitle())==false)
		{
			NewDTO dtoAfterSaveFile = upLoadFileUtils.uploadFile(dto);
			//after upload img convert dto to entity, set cate, save
			newEntity = newConverter.toEntity(dtoAfterSaveFile);
			newEntity.setCategory(category);
		}
		return newConverter.toDto(newRepository.save(newEntity));
	}

	@Override
	@Transactional
	public void delete(long[] ids) 
	{
		for (long id: ids) 
		{
			newRepository.deleteById(id);
		}
	}
	
	@Override
	public List<NewDTO> findAll() 
	{
		List<NewEntity> listAllNewEntity = newRepository.findAll();
		List<NewDTO> listAllNewDTO = new ArrayList<NewDTO>();
		for (NewEntity newEntity : listAllNewEntity) 
		{
			listAllNewDTO.add(newConverter.toDto(newEntity));
		}
		return listAllNewDTO;
	}
	
	@Override
	public List<NewDTO> findByTitleContaining(String searchWord)
	{
		List<NewEntity> listNewEntity = newRepository.findByTitleContaining(searchWord);
		List<NewDTO> listNewDTO = new ArrayList<>();
		for (NewEntity newEntity : listNewEntity) 
		{
			listNewDTO.add(newConverter.toDto(newEntity));
		}
		return listNewDTO;
	}
	
	@Override
	public int countByCategory_id(long categoryid)
	{
		return newRepository.countByCategory_id(categoryid);
	}
}
