package com.example.hibernatevalidatordemo.pojo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户信息
 */
@Data
@AllArgsConstructor
public class User {
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @Min(value = 0)
    @Max(value = 150)
    private Integer age;

    @NotBlank
    @Email
    private String email;

}
