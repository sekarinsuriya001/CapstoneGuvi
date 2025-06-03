package com.busticketbooking.NammaOoruBus.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 4, message = "Name must be at least 4 characters long")
    private String name;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}",
            message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit"
    )
    private String password;

    @NotNull(message = "Phone Number should not be empty")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must be 10 digits long"
    )
    private String phoneNumber;

    @NotEmpty(message = "role should not be empty")
    private String role;
    @NotNull(message = "age should not be empty")
    private Integer age;
    @NotEmpty(message = "gender should not be empty")
    private String gender;
}