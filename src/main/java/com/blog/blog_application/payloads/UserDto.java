package com.blog.blog_application.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;

    @NotEmpty
    @Size(min = 3, message = "Name must be more than 3 characters")
    private String name;

    @Email
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be min 3 & max 10 characters")
    private String password;

    @NotEmpty
    @Size(min = 3, message = "About must be more than 3 characters")
    private String about;
}
