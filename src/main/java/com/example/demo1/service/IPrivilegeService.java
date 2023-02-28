package com.example.demo1.service;

import com.example.demo1.dto.PrivilegeDTO;
import com.example.demo1.entity.PrivilegeEntity;

import java.util.List;

public interface IPrivilegeService {
    public List<PrivilegeDTO> findAll();
    public PrivilegeEntity findOneByCode(String code);
}
