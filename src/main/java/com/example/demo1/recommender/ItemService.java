package com.example.demo1.recommender;

import com.example.demo1.dto.NewDTO;

import java.util.List;

public interface ItemService {
    List<NewDTO> fillItemsItemBased(long userId);
    List<NewDTO> fillItemsUserBased(long userId);
}
