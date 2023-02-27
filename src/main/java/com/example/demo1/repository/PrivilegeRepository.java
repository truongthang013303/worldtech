package com.example.demo1.repository;

import com.example.demo1.entity.PrivilegeEntity;
import netscape.security.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long>
{

}
