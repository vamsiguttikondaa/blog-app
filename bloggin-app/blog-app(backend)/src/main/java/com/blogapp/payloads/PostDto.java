package com.blogapp.payloads;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private LocalDateTime addedDate;
    private Integer likes;

    private Long userId;        // to link the post to a user
    private Integer categoryId; // to link the post to a category
}
