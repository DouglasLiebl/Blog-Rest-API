package io.github.douglasliebl.blogrestapi.service.impl;

import io.github.douglasliebl.blogrestapi.entity.Post;
import io.github.douglasliebl.blogrestapi.exception.ResourceNotFoundException;
import io.github.douglasliebl.blogrestapi.payload.PostDto;
import io.github.douglasliebl.blogrestapi.repository.PostRepository;
import io.github.douglasliebl.blogrestapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        postRepository.save(post);

        return mapToDTO(post);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> mapToDTO(post))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {
        return mapToDTO(postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)));
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
       var post = postRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());

       return mapToDTO(postRepository.save(post));
    }

    @Override
    public void deletePost(Long id) {
        if(postRepository.findById(id).isEmpty()) throw new ResourceNotFoundException("Post", "id", id);
        postRepository.deleteById(id);
    }

    public Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    public PostDto mapToDTO(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }
}
