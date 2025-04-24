package com.blogapp.controllers;

import com.blogapp.payloads.CommentDto;
import com.blogapp.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentCOntroller {

    @Autowired
    private CommentService commentService;

    // ✅ Only logged-in users can add comments
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDto> createComment(
            @Valid @RequestBody CommentDto commentDto,
            @PathVariable Integer postId,
            @PathVariable Long userId
    ) {
        CommentDto createdComment = commentService.createComment(commentDto, postId, userId);
        return ResponseEntity.ok(createdComment);
    }

    // ✅ Anyone can view comments
    @GetMapping("/view/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Integer postId) {
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // ✅ Only users can update their own comments
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @Valid @RequestBody CommentDto commentDto,
            @PathVariable Integer commentId
    ) {
        CommentDto updated = commentService.updateComment(commentDto, commentId);
        return ResponseEntity.ok(updated);
    }

    // ✅ Only admin or the comment owner can delete
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/del/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
