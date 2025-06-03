package com.busticketbooking.NammaOoruBus.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
    private Integer age;
    private String gender;
}