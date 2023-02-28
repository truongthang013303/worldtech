package com.example.demo1.service.impl;

import com.example.demo1.converter.PrivilegeConverter;
import com.example.demo1.dto.PrivilegeDTO;
import com.example.demo1.entity.PrivilegeEntity;
import com.example.demo1.repository.PrivilegeRepository;
import com.example.demo1.service.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrivilegeService implements IPrivilegeService {
    @Autowired
    PrivilegeRepository privilegeRepository;
    @Autowired
    PrivilegeConverter privilegeConverter;
    public List<PrivilegeDTO> findAll()
    {
        List<PrivilegeEntity> allEnity = privilegeRepository.findAll();
        List<PrivilegeDTO> allDTO = new ArrayList<PrivilegeDTO>();
        for (PrivilegeEntity entity : allEnity)
        {
            PrivilegeDTO dto = privilegeConverter.toDto(entity);
            allDTO.add(dto);
        }
        return allDTO;
    }

    @Override
    public PrivilegeEntity findOneByCode(String code) {
        return privilegeRepository.findOneByCode(code);
    }
}
