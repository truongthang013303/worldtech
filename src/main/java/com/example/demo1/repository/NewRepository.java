package com.example.demo1.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo1.entity.NewEntity;
import org.springframework.data.jpa.repository.Query;

public interface NewRepository extends JpaRepository<NewEntity, Long> 
{
	/*	@Query("select n.id from NewEntity n where n.status = 1 AND n.title LIKE '%?1%'")
	List<NewEntity> findByTitleContainingStatusIsAccepted(String title);*/
	List<NewEntity> findByTitleContainingAndStatus(String title, Integer status);
	List<NewEntity> findByTitleContainingAndCreatedBy(String title, String createdBy);
	List<NewEntity> findByTitleContaining(String title);
/*	@Query("select n.id from NewEntity n where n.status = 1")
	List<Integer> findAllNewsAccepted();*/
	@Query("select new NewEntity(n.id, n.title) FROM NewEntity n WHERE n.id=?1")
	NewEntity findOneNewById_Custom(Long id);
	NewEntity findOneById(Long id);
	NewEntity findOneByIdAndStatus(Long id, Integer status);
	Page<NewEntity> findAllByCategoryCode(String cateCode,Pageable pageable);
	Page<NewEntity> findAllByCategoryId(Long categoryid,Pageable pageable);
	Page<NewEntity> findAllByCreatedBy(String createdBy, Pageable pageable);
	Page<NewEntity> findAllByCategoryCodeAndStatus(String cateCode, Integer status, Pageable pageable);
	Page<NewEntity> findAllByStatus(Integer status,Pageable pageable);
	boolean existsByTitle(String title);
	Long countByCategory_id(long Category_id);
	Long countByStatus(Integer status);
	Page<NewEntity> findAll(Pageable pageable);
}

