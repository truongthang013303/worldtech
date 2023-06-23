package com.example.demo1.repository;

import com.example.demo1.dto.CommentDTO;
import com.example.demo1.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findAllByPost_Id(Long id, Pageable pageable);

    //Just work with spring data jpa <2.0
    //void deleteAll(List<CommentEntity> commentEntities);

    @Modifying
    @Query("delete from CommentEntity c where c.id in ?1")
    void deleteCommentsWithIds(List<Integer> ids);

    //I have use this function for delete list of elements in JPA repository
    //void deleteInBatch(List<Integer> list);

    List<CommentEntity> findByContentContaining(String keyword);
}
