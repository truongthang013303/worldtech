package com.example.demo1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class CommentEntity extends BaseEntity{
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newid")
    @JsonIgnore
    private NewEntity post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    @JsonIgnore
    private UserEntity user;
}
