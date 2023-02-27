package com.example.demo1.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo1.entity.NewEntity;

public interface NewRepository extends JpaRepository<NewEntity, Long> 
{
	List<NewEntity> findByTitleContaining(String title);
	NewEntity findOneById(Long id);	
	Page<NewEntity> findAllByCategoryCode(String cateCode,Pageable pageable);	
	boolean existsByTitle(String title);
	int countByCategory_id(long Category_id);
}

