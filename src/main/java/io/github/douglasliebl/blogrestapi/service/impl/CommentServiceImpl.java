package io.github.douglasliebl.blogrestapi.service.impl;

import io.github.douglasliebl.blogrestapi.entity.Comment;
import io.github.douglasliebl.blogrestapi.entity.Post;
import io.github.douglasliebl.blogrestapi.exception.BlogApiException;
import io.github.douglasliebl.blogrestapi.exception.ResourceNotFoundException;
import io.github.douglasliebl.blogrestapi.payload.CommentDto;
import io.github.douglasliebl.blogrestapi.repository.CommentRepository;
import io.github.douglasliebl.blogrestapi.repository.PostRepository;
import io.github.douglasliebl.blogrestapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);
        repository.save(comment);

        return mapToDTO(comment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPost(Long postId) {
        List<Comment> comments = repository.findByPostId(postId);

        return comments.stream()
                .map(comment -> mapToDTO(comment))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Comment comment = getComment(postId, commentId);
        return mapToDTO(comment);
    }

    @Override
    @Transactional
    public CommentDto updateComment(CommentDto commentDto, Long postId, Long commentId) {
        Comment comment = getComment(postId, commentId);

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return mapToDTO(repository.save(comment));
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = getComment(postId, commentId);
        repository.delete(comment);
    }


    private CommentDto mapToDTO(Comment comment) {
        return mapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }

    public Comment getComment(Long postId, Long commentId) {Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        return comment;
    }
}
