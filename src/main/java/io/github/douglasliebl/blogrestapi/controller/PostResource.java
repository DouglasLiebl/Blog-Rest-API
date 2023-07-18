package io.github.douglasliebl.blogrestapi.controller;

import io.github.douglasliebl.blogrestapi.payload.PostDto;
import io.github.douglasliebl.blogrestapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostResource {

    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDto request) {
        var post = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPostById(@PathVariable("id") Long id) {
        var post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePost(@RequestBody PostDto request,
                                     @PathVariable("id") Long id) {
        var post = postService.updatePost(request, id);
        return ResponseEntity.ok().body(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post successfully deleted");
    }
}
