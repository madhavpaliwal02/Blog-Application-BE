package com.blog.blog_application.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.blog_application.exception.PostException;
import com.blog.blog_application.exception.ResourceNotFoundException;
import com.blog.blog_application.model.Category;
import com.blog.blog_application.model.Post;
import com.blog.blog_application.model.User;
import com.blog.blog_application.payloads.PostDto;
import com.blog.blog_application.payloads.PostResponse;
import com.blog.blog_application.repositories.CategoryRepo;
import com.blog.blog_application.repositories.PostRepo;
import com.blog.blog_application.repositories.UserRepo;
import com.blog.blog_application.serviceImpl.services.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final ModelMapper mapper;

    // Create Post
    @Override
    public PostDto createPost(PostDto postDto, int userId, int catId) throws PostException {
        // Check for valid post
        checkNullPost(postDto);

        // check for existing post
        boolean existPost = this.postRepo.findAll().stream().filter((p) -> p.getTitle().equals(postDto.getTitle()) &&
                p.getContent().equals(postDto.getContent())).findAny().isPresent();

        if (existPost)
            throw new PostException("Post Details already exists");

        // Checking for User & Category
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Category category = this.categoryRepo.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "catId", catId));

        // Creating new post
        Post post = this.mapToPost(postDto);
        post.setImageName("default.png");
        post.setAddedDate(new Date());

        post.setUser(user);
        post.setCategory(category);

        // Saving new post
        Post savePost = this.postRepo.save(post);
        // System.out.println("Post: " + post);
        // System.out.println("savePost: " + savePost);
        return this.mapToDto(savePost);
    }

    // Get all post
    @Override
    public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir) throws PostException {
        // List<PostDto> list = this.postRepo.findAll().stream().map((post) ->
        // this.mapToDto(post))
        // .collect(Collectors.toList());
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<PostDto> postDtos = pagePost.getContent().stream().map((post) -> this.mapToDto(post))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos); // List of PostDto

        postResponse.setPageNumber(pagePost.getNumber()); // Curr page no
        postResponse.setPageSize(pagePost.getSize()); // Page size

        postResponse.setTotalElements(pagePost.getTotalElements()); // Total elements
        postResponse.setTotalPage(pagePost.getTotalPages()); // Total page

        postResponse.setLastPage(pagePost.isLast()); // Is last page

        return postResponse;
    }

    // Get Post By Id
    @Override
    public PostDto getPostById(int id) throws PostException {
        Post post = this.getPostHelper(id);
        return this.mapToDto(post);
    }

    // Update post
    @Override
    public PostDto updatePost(int id, PostDto postDto) throws PostException {
        checkNullPost(postDto);

        // Getting old post and modify
        Post oldPost = this.getPostHelper(id);
        oldPost.setTitle(postDto.getTitle());
        oldPost.setContent(postDto.getContent());
        if (!(postDto.getImageName() == null || postDto.getImageName().equals("")))
            oldPost.setImageName(postDto.getImageName());

        // saving and returning
        Post newPost = this.postRepo.save(oldPost);
        return this.mapToDto(newPost);
    }

    // Delete Post
    @Override
    public void deletePost(int id) throws PostException {
        Post post = this.getPostHelper(id);
        this.postRepo.delete(post);
    }

    // Find Posts by User
    @Override
    public List<PostDto> findByUser(int userId) throws PostException {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<PostDto> list = this.postRepo.findByUser(user).stream().map((post) -> this.mapToDto(post))
                .collect(Collectors.toList());
        return list;
    }

    // Find Posts by Category
    @Override
    public List<PostDto> findByCategory(int catId) throws PostException {
        Category cat = this.categoryRepo.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "catId", catId));
        List<PostDto> list = this.postRepo.findByCategory(cat).stream().map((post) -> this.mapToDto(post))
                .collect(Collectors.toList());
        return list;
    }

    // Find posts by search keyword
    @Override
    public List<PostDto> findBySearch(String keyword) throws PostException {
        List<PostDto> list = this.postRepo.findByTitleContaining(keyword).stream().map((post) -> this.mapToDto(post))
                .collect(Collectors.toList());
        return list;
    }

    /* Helper get Post */
    public Post getPostHelper(int id) {
        return this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", id));
    }

    /* Helper : Post to PostDto */
    private PostDto mapToDto(Post post) {
        return this.mapper.map(post, PostDto.class);
    }

    /* Helper : PostDto to Post */
    private Post mapToPost(PostDto dto) {
        return this.mapper.map(dto, Post.class);
    }

    /* Helper : Check user */
    private void checkNullPost(PostDto post) throws PostException {
        if (post == null)
            throw new PostException("Post details can't be null");
    }

}
