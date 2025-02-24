package com.hj.springboot.web.domain.posts;

import com.hj.springboot.web.dto.PostsResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    void 게시글저장_불러오기() throws Exception {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("hj")
                .build());

        // when
        List<Posts> list = postsRepository.findAll();

        // then
        Posts posts = list.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.of(2025, 1, 1, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> all = postsRepository.findAll();

        // then
        Posts posts = all.get(0);
        System.out.println(">>>>>>>>> createdDate=" + posts.getCreatedDate()
                + ", modifiedDate=" + posts.getModifiedDate());
        assertThat(posts.getCreatedDate())
                .isAfter(now);
        assertThat(posts.getModifiedDate())
                .isAfter(now);
    }

    @Test
    void 게시글_제목으로_조회() throws Exception {
        // given
        String title = "게시글 title";
        String content = "테스트 content";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("hj")
                .build());

        // when
        List<PostsResponseDto> list = postsRepository.findByTitle(1, title);

        // then
        PostsResponseDto postDto = list.get(0);
        assertThat(postDto.getTitle()).isEqualTo(title);
    }

    @Test
    void 게시글_작성자로_조회() throws Exception {
        // given
        String title = "게시글 title";
        String content = "테스트 content";
        String author = "테스트 hj";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        // when
        List<PostsResponseDto> list = postsRepository.findByAuthor(1, title);

        // then
        PostsResponseDto postDto = list.get(0);
        assertThat(postDto.getTitle()).isEqualTo(title);
    }
}