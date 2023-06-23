package com.example.demo1.repository;

import com.example.demo1.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long>
{
    PrivilegeEntity findOneByCode(String code);
    PrivilegeEntity findOneById(Long id);
    boolean existsByCode(String code);
    List<PrivilegeEntity> findByNameContaining(String name);
}
