package com.hj.springboot.service.posts;

import com.hj.springboot.web.domain.posts.Posts;
import com.hj.springboot.web.domain.posts.PostsRepository;
import com.hj.springboot.web.dto.PostsListResponseDto;
import com.hj.springboot.web.dto.PostsResponseDto;
import com.hj.springboot.web.dto.PostsSaveRequestDto;
import com.hj.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PostsService {
    private final PostsRepository postsRepository;

    private final int pageSize = 10;

    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc(pageSize).stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public List<PostsResponseDto> findByTitle(String title) {
        return postsRepository.findByTitle(pageSize, title);
    }

    @Transactional(readOnly = true)
    public List<PostsResponseDto> findByAuthor(String author) {
        return postsRepository.findByAuthor(pageSize, author);
    }
}
