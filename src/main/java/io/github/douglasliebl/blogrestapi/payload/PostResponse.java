package io.github.douglasliebl.blogrestapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private List<PostDto> content;

    private int PageNo;

    private int pageSize;

    private Long totalElements;

    private int totalPages;

    private boolean last;
}
