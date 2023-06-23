package com.example.demo1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.geometry.Pos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "new")
@NoArgsConstructor
@Getter
@Setter
public class NewEntity extends BaseEntity {
	@Column(name = "status")
	private Integer status;
	@Column(name = "title")
	private String title;
	
	@Column(name = "thumbnail")
	private String thumbnail;
	
	@Column(name = "shortdescription", columnDefinition = "TEXT")
	private String shortDescription;
	
	@Column(name = "content", columnDefinition = "TEXT")
	private String content;
	
/*	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;*/
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

	private String message;

	@OneToMany(mappedBy = "post" , cascade = CascadeType.ALL)
	private Collection<CommentEntity> comments;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
	Set<PostRating> ratings;

	public NewEntity(Long id, String title) {
		this.title = title;
		super.setId(id);
	}

	public boolean isNotHaveCategory(){
		if(this.category==null)
		{
			return true;
		}
		return false;
	}
	//sync bidriectional both sides
	public void addRating(UserEntity userEntity, Integer rating){
		PostRating postRating = new PostRating(PostRatingKey.builder()
				.userId(userEntity.getId())
				.postId(this.getId())
				.build(), userEntity, this, rating);
		ratings.add(postRating);
		userEntity.getRatings().add(postRating);
	}
	//sync bidriectional both sides
	public void addRating(PostRating postRating){
		ratings.add(postRating);
		postRating.setNews(this);
	}
	/*	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}*/
}
