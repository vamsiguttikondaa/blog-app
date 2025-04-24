package com.blogapp.payloads;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    private String content;
    private Integer postId;
    private Long userId;
}