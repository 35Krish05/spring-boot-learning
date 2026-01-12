package com.example.pagination.repository;


import com.example.pagination.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

//    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
//    List<Post> findPostsByUserId(@Param("userId") Long userId);
//
//    @Query("""
//        SELECT p FROM Post p
//        WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
//    """)
//    List<Post> findPostsByTitleKeyword(@Param("keyword") String keyword);
//
//    @Query("""
//        SELECT p FROM Post p
//        WHERE p.createdAt >= :cutoff
//        ORDER BY p.createdAt DESC
//    """)
//    Page<Post> findRecentPosts(
//            @Param("cutoff") LocalDateTime cutoff,
//            Pageable pageable
//    );
}