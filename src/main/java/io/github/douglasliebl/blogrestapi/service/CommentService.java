package io.github.douglasliebl.blogrestapi.service;

import io.github.douglasliebl.blogrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long postId, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPost(Long id);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(CommentDto commentDto, Long postId, Long commentId);

    void deleteComment(Long postId, Long commentId);
}
