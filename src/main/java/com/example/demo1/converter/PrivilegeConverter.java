package com.example.demo1.converter;

import com.example.demo1.dto.PrivilegeDTO;
import com.example.demo1.dto.RoleDTO;
import com.example.demo1.entity.PrivilegeEntity;
import com.example.demo1.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class PrivilegeConverter {

    public PrivilegeDTO toDto(PrivilegeEntity entity) {
        PrivilegeDTO result = new PrivilegeDTO();
        result.setId(entity.getId());
        result.setName(entity.getName());
        result.setCode(entity.getCode());
        result.setCreatedBy(entity.getCreatedBy());
        result.setCreatedDate(entity.getCreatedDate());
        result.setModifiedDate(entity.getModifiedDate());
        result.setModifiedBy(entity.getModifiedBy());
        return result;
    }

    public PrivilegeEntity toEntity(PrivilegeDTO dto) {
        PrivilegeEntity result = new PrivilegeEntity();
        result.setName(dto.getName());
        result.setCode(dto.getCode());
        return result;
    }

    public PrivilegeEntity toEntity(PrivilegeEntity entity, PrivilegeDTO dto)
    {
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        return entity;
    }
}
