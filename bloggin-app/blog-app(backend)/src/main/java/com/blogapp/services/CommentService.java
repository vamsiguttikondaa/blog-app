package com.blogapp.services;



import java.util.List;

import com.blogapp.payloads.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer postId, Long userId);

    List<CommentDto> getCommentsByPostId(Integer postId);

    CommentDto updateComment(CommentDto commentDto, Integer commentId);

    void deleteComment(Integer commentId);
}
