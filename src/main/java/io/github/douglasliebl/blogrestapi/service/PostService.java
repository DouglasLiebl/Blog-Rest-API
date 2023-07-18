package io.github.douglasliebl.blogrestapi.service;

import io.github.douglasliebl.blogrestapi.payload.PostDto;
import io.github.douglasliebl.blogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(Long id);
}
