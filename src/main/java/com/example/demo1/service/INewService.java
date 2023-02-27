package com.example.demo1.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo1.dto.NewDTO;

public interface INewService {

	List<NewDTO> findByTitleContaining(String searchWord);

	List<NewDTO> findAll();
	Page<NewDTO> findByPage(Pageable pageable);

	void delete(long[] ids);

	NewDTO saveOrUpdate(NewDTO dto);

	List<NewDTO> findAllByCategoryCode(String cateCode, Pageable pageable);

	Page<NewDTO> findAllByCategoryCodePage(String cateCode, Pageable pageable);
	NewDTO findById(long id);

	int getTotalItem();

	List<NewDTO> findAll(Pageable pageable);

	int countByCategory_id(long categoryid);

	int getTotalItemByCategory_id(long categoryId);
}
