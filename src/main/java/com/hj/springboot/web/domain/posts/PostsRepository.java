package com.hj.springboot.web.domain.posts;

import com.hj.springboot.web.dto.PostsResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("""
            SELECT p
            FROM Posts p
            ORDER BY p.id DESC
            LIMIT :count
            """)
    List<Posts> findAllDesc(int count);

    @Query("""
            SELECT p
            FROM Posts p
            WHERE p.title like :title%
            ORDER BY p.id DESC
            LIMIT :count
            """)
    List<PostsResponseDto> findByTitle(int count, String title);


    @Query("""
            SELECT p
            FROM Posts p
            WHERE p.title like :author%
            ORDER BY p.id DESC
            LIMIT :count
            """)
    List<PostsResponseDto> findByAuthor(int count, String author);
}
