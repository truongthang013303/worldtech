package com.example.demo1.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRatingKey implements Serializable {
    @Column(name = "user_id")
    Long userId;

    @Column(name = "post_id")
    Long postId;

    @Override
    public int hashCode() {
        return Objects.hash(postId, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        PostRatingKey that = (PostRatingKey) obj;
        return Objects.equals(postId, that.postId) &&
                Objects.equals(userId, that.userId);
    }
}
