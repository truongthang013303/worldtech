package com.example.demo1.service.impl;

import com.example.demo1.converter.PrivilegeConverter;
import com.example.demo1.dto.PrivilegeDTO;
import com.example.demo1.entity.PrivilegeEntity;
import com.example.demo1.repository.PrivilegeRepository;
import com.example.demo1.service.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PrivilegeService implements IPrivilegeService {
    @Autowired
    PrivilegeRepository privilegeRepository;
    @Autowired
    PrivilegeConverter privilegeConverter;
    @PostFilter("filterObject.createdBy == authentication.name")
    public List<PrivilegeDTO> findAll()
    {
        List<PrivilegeEntity> allEnity = privilegeRepository.findAll();
        List<PrivilegeDTO> allDTO = new ArrayList<PrivilegeDTO>();
        for (PrivilegeEntity entity : allEnity)
        {
            PrivilegeDTO dto = privilegeConverter.toDto(entity);
            allDTO.add(dto);
        }
/*        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(p);
        System.out.println(au);*/

        return allDTO;
    }

    @Override
    public PrivilegeEntity findOneByCode(String code) {
        return privilegeRepository.findOneByCode(code);
    }

    @Override
    public PrivilegeDTO findOneById(Long id) {
        return privilegeConverter.toDto(privilegeRepository.findOneById(id));
    }

    @Override
    @Transactional
    public PrivilegeDTO saveOrUpdate(PrivilegeDTO dto) {
        PrivilegeEntity entity = new PrivilegeEntity();
        if(dto.getId()!=null)
        {
            PrivilegeEntity oldEntity = privilegeRepository.findOneById(dto.getId());
            entity = privilegeConverter.toEntity(oldEntity,dto);
        }
        else if(privilegeRepository.existsByCode(dto.getCode())==false) //roleRepository.findExistByCode(dto.getCode())==0
        {
            entity = privilegeConverter.toEntity(dto);
            System.out.println("Chua ton tai");
        }
        else
        {
            System.out.println("Da ton tai");
            return null;
        }
        PrivilegeEntity entitySaved = privilegeRepository.save(entity);
        return privilegeConverter.toDto(entitySaved);
    }
    @Override
    @Transactional
    public void delete(long[] ids)
    {
        if(ids.length!=0)
        {
            for (long id: ids)
            {
                privilegeRepository.deleteById(id);
            }
        }
    }
    @Override
    public List<PrivilegeDTO> findByNameContaining(String name){
        List<PrivilegeEntity> entities = privilegeRepository.findByNameContaining(name);
        List<PrivilegeDTO> dtos = entities.stream().map(c -> privilegeConverter.toDto(c)).collect(Collectors.toList());
        return dtos;
    }


    @Override
    public Page<PrivilegeDTO> findByPage(Pageable pageable) {
        Page<PrivilegeEntity> entities = privilegeRepository.findAll(pageable);
        Page<PrivilegeDTO> dtoPage = convertPageEntityToPageDTO(entities);
        return dtoPage;
    }

    @Override
    public Page<PrivilegeDTO> convertPageEntityToPageDTO(Page<PrivilegeEntity> entities)
    {
        Page<PrivilegeDTO> dtoPage = entities.map(new Function<PrivilegeEntity, PrivilegeDTO>() {
            @Override
            public PrivilegeDTO apply(PrivilegeEntity entity) {
                PrivilegeDTO dto = new PrivilegeDTO();
                dto = privilegeConverter.toDto(entity);
                return dto;
            }
        });
        return dtoPage;
    }
}
