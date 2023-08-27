package com.blog.blog_application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blog_application.exception.PostException;
import com.blog.blog_application.payloads.PostDto;
import com.blog.blog_application.serviceImpl.services.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostCtrl {

    private final PostService postService;

    // Create Post
    @PostMapping("/user/{uid}/category/{cid}/post")
    public ResponseEntity<PostDto> createPostHandler(@RequestBody PostDto post, @PathVariable("uid") int userId,
            @PathVariable("cid") int cat_Id) throws PostException {
        PostDto savePost = this.postService.createPost(post, userId, cat_Id);
        return new ResponseEntity<>(savePost, HttpStatus.CREATED);
    }

    // Get All Posts
    @GetMapping("/post")
    public ResponseEntity<List<PostDto>> getAllPostHandler() throws PostException {
        List<PostDto> list = this.postService.getAllPost();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Get Post By Id
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostByIdHandler(@PathVariable("postId") int id) throws PostException {
        PostDto post = this.postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Get all Posts by User
    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<PostDto>> getPostByUserHandler(@PathVariable("userId") int id) throws PostException {
        List<PostDto> list = this.postService.findByUser(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Get all Posts by Category
    @GetMapping("/category/{catId}/post")
    public ResponseEntity<List<PostDto>> getPostByCategoryHandler(@PathVariable("catId") int id) throws PostException {
        List<PostDto> list = this.postService.findByCategory(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Update Post
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePostHandler(@RequestBody PostDto post, @PathVariable("postId") int id)
            throws PostException {
        PostDto updatePost = this.postService.updatePost(id, post);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.ACCEPTED);
    }

    // Delete Post
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePostHandler(@PathVariable("postId") int id) throws PostException {
        this.postService.deletePost(id);
        return new ResponseEntity<>("Sucessfully deleted post", HttpStatus.OK);
    }
}
