package com.mappers;

import com.dto.PostDto;
import com.model.Post;

import static com.utils.DateFormatterUtils.formatDate;
import static com.utils.DateFormatterUtils.parseDate;

/**
 * Post mapper.
 */
public class PostMapper {

    /**
     * Convert from post to dto.
     *
     * @param post post.
     * @return dto.
     */
    public PostDto mapFromPostToDto(final Post post) {
        final PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setName(post.getName());
        postDto.setDate(formatDate(post.getDate()));
        postDto.setPhone(post.getPhone());
        postDto.setText(post.getText());
        return postDto;
    }

    /**
     * Convert from dto to post.
     *
     * @param postDto post dto.
     * @return post.
     */
    public Post mapFromDtoToPost(final PostDto postDto) {
        final Post post = new Post();
        post.setName(postDto.getName());
        post.setDate(parseDate(postDto.getDate()));
        post.setPhone(postDto.getPhone());
        post.setText(postDto.getText());
        return post;
    }
}
