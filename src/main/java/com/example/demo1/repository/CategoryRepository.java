package com.example.demo1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo1.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> 
{
	CategoryEntity findOneByCode(String code);
	CategoryEntity findOneById(Long id);
	boolean existsByCode(String code);
}
