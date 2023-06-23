package com.example.demo1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.NamedEntityGraph;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    @NotNull
    @Positive
    @Max(value = 5, message = "ratingScore must be less than 5")
    @Min(value = 1, message = "ratingScore must be equals or great than 1")
    private Integer rating;
    private Long userid;
    @NotNull
    private Long postid;
}
