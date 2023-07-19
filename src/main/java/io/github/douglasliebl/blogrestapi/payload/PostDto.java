package io.github.douglasliebl.blogrestapi.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {

    private Long id;

    @NotEmpty(message = "Title should not be empty or null")
    @Size(min = 2, message = "Title should have at least 2 characters")
    private String title;

    @NotEmpty(message = "Post description should not be empty or null")
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @NotEmpty(message = "Post content should not be empty or null")
    private String content;
    private Set<CommentDto> comments;
}
