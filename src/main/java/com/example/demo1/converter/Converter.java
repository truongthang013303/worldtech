package com.example.demo1.converter;

import com.example.demo1.dto.CategoryDTO;
import com.example.demo1.entity.CategoryEntity;

public interface Converter<entityType, dtoType>{
    public dtoType toDto(entityType entity);
    public entityType toEntity(dtoType dto);
    public entityType toEntity(entityType entity, dtoType dto);
}
