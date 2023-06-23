package com.example.demo1.converter;

import com.example.demo1.dto.AbstractDTO;
import com.example.demo1.entity.BaseEntity;
import org.springframework.data.domain.Page;

public interface ConverterPage {
    //Mỗi page như: news, category, user... sẽ có implement riêng cho phương thức này
    //Nhưng tóm lại đều nhận vào 1 PageEntity và convert sang 1 PageDTO
    Page< ? extends AbstractDTO> convertPageEntityToPageDTO(Page<? extends BaseEntity> entities);
}
