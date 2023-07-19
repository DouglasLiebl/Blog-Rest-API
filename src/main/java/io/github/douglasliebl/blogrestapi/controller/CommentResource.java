package io.github.douglasliebl.blogrestapi.controller;

import io.github.douglasliebl.blogrestapi.payload.CommentDto;
import io.github.douglasliebl.blogrestapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentResource {

    private final CommentService commentService;

    @PostMapping("/{post_id}/comments")
    public ResponseEntity createComment(@PathVariable("post_id") Long id,
                                        @RequestBody CommentDto request) {
        var comment = commentService.createComment(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @GetMapping("/{post_id}/comments")
    public ResponseEntity getCommentsByPost(@PathVariable("post_id") Long id) {
        var comments = commentService.getAllCommentsByPost(id);
        return ResponseEntity.ok().body(comments);
    }

    @GetMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity getCommentById(@PathVariable("post_id") Long postId,
                                         @PathVariable("comment_id") Long commentId) {
        var comment = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok().body(comment);
    }

    @PutMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity updateComment(@PathVariable("post_id") Long postId,
                                        @PathVariable("comment_id") Long commentId,
                                        @RequestBody CommentDto request) {
        var comment = commentService.updateComment(request, postId, commentId);
        return ResponseEntity.ok().body(comment);
    }

    @DeleteMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity deleteComment (@PathVariable("post_id") Long postId,
                                         @PathVariable("comment_id") Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comment successfully deleted");
    }

}
