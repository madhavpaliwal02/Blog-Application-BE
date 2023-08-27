package com.blog.blog_application.payloads;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto {

    private int id;

    @NotEmpty(message = "Title must not be empty")
    @Size(min = 3, message = "Title atleast have 3 letters")
    private String title;

    @NotEmpty(message = "Content must not be empty")
    @Size(min = 3, message = "Content atleast have 3 letters")
    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;
}
