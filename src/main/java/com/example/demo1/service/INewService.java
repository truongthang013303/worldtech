package com.example.demo1.service;

import java.util.List;

import com.example.demo1.dto.PostStatus;
import com.example.demo1.dto.RatingDTO;
import com.example.demo1.entity.NewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo1.dto.NewDTO;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface INewService {
	List<NewDTO> findByTitleContainingAndStatus(String searchWord);
	List<NewDTO> findByTitleContaining(String searchWord);
	List<NewDTO> findAll();

	Page<NewDTO> findAllByCreatedBy(String createdBy, Pageable pageable);

	Page<NewDTO> findByPage(Pageable pageable);
	Page<NewDTO> findByPage(Integer status, Pageable pageable);
	void delete(long[] ids);
	NewDTO saveOrUpdate(NewDTO dto) throws Exception;
	List<NewDTO> findAllByCategoryCode(String cateCode, Pageable pageable);
	Page<NewDTO> findAllByCategoryCode_PageReturn(String cateCode, Pageable pageable);
	Page<NewDTO> findAllByCategoryCodePage(String cateCode, Pageable pageable);
	Page<NewDTO> findAllByCategoryCodePage(String cateCode, Integer status, Pageable pageable);
	Page<NewDTO> findAllByCategoryCodePage(String cateCode, Integer status, Integer page, Integer size, Sort.Direction direction, String... properties);
	NewDTO findById(long id);
	NewDTO findByIdAndStatus(long id, int status);
	NewDTO findByIdAdmin(long id);
	Long getTotalItem();
    NewEntity rating(Long postid, Long userid, Integer rating);
	ResponseEntity<?> rating(RatingDTO ratingDTO);
	ResponseEntity<?> rating(Authentication authentication, RatingDTO ratingDTO);
    List<NewDTO> findAll(Pageable pageable);
	List<NewDTO> getPostsOfAnUser();
	List<NewDTO> findByTitleContainingAndCreatedBy(String search, String createdBy);
	Long countByCategory_id(long categoryid);
	Long getTotalItemByCategory_id(long categoryId);
	NewDTO publishPost(NewDTO dto);
	Long countByStatus(Integer status);
	boolean existsById(Long id);

	Page<NewDTO> findAllByCategoryId(Long categoryid, Pageable pageable);
}
