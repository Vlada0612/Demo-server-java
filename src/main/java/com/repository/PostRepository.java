package com.repository;

import com.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Post repository.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
