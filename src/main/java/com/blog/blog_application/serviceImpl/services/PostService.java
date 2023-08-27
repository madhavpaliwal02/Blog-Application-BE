package com.blog.blog_application.serviceImpl.services;

import java.util.List;

import com.blog.blog_application.exception.PostException;
import com.blog.blog_application.payloads.PostDto;
import com.blog.blog_application.payloads.PostResponse;

public interface PostService {

    // create post
    public PostDto createPost(PostDto postDto, int userId, int catId) throws PostException;

    // get All post
    public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir) throws PostException;

    // get post by id
    public PostDto getPostById(int id) throws PostException;

    // update post
    public PostDto updatePost(int id, PostDto postDto) throws PostException;

    // delete post
    public void deletePost(int id) throws PostException;

    // get posts by user
    public List<PostDto> findByUser(int userId) throws PostException;

    // get posts by category
    public List<PostDto> findByCategory(int catId) throws PostException;

    // get posts by search
    public List<PostDto> findBySearch(String keyword) throws PostException;
}
