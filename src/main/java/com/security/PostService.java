package com.security;

import com.dto.PostDto;
import com.exception.PostNotFoundException;
import com.mappers.PostMapper;
import com.model.Post;
import com.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.utils.StreamUtils.listConversion;

/**
 * Post Service.
 */
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    private PostMapper postMapper;

    /**
     * Show all posts.
     *
     * @return list of posts.
     */
    @Transactional
    public List<PostDto> showAllPosts() {
        return listConversion(postRepository.findAll(), post -> postMapper.mapFromPostToDto(post));
    }

    /**
     * Create of post.
     *
     * @param postDto post dto.
     */
    @Transactional
    public void createPost(final PostDto postDto) {
        final Post post = postMapper.mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    /**
     * Read one post.
     *
     * @param id id of post.
     * @return post dto.
     */
    @Transactional
    public PostDto readSinglePost(final Long id) {
        final Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return postMapper.mapFromPostToDto(post);
    }


}
