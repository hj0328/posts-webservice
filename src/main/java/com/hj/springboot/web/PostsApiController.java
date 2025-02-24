package com.hj.springboot.web;

import com.hj.springboot.service.posts.PostsService;
import com.hj.springboot.web.dto.PostsResponseDto;
import com.hj.springboot.web.dto.PostsSaveRequestDto;
import com.hj.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts/title/{title}")
    public List<PostsResponseDto> findByTitle(@PathVariable String title) {
        return postsService.findByTitle(title);
    }

    @GetMapping("/api/v1/posts/author/{author}")
    public List<PostsResponseDto> findByAuthor(@PathVariable String author) {
        return postsService.findByAuthor(author);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
