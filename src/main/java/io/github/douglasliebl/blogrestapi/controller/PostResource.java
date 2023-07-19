package io.github.douglasliebl.blogrestapi.controller;

import io.github.douglasliebl.blogrestapi.payload.PostDto;
import io.github.douglasliebl.blogrestapi.payload.PostResponse;
import io.github.douglasliebl.blogrestapi.service.PostService;
import io.github.douglasliebl.blogrestapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity getAllPosts(@RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                      @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
                                      @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir) {
        PostResponse posts = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
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
