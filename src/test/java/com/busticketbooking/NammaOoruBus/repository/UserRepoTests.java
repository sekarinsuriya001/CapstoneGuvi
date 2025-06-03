package com.busticketbooking.NammaOoruBus.repository;

import com.busticketbooking.NammaOoruBus.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test cases for UserRepo")
@DataMongoTest
public class UserRepoTests {

    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    void cleanDatabase() {
        userRepo.deleteAll();
    }

    @Test
    @DisplayName("Test case for save user operation")
    public void givenUser_whenSave_thenReturnSavedUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("Password@123");
        user.setPhoneNumber("12345678900");
        user.setRole("Admin");
        user.setAge(30);
        user.setGender("Male");
        User savedUser = userRepo.save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("johndoe@example.com");
    }

    @Test
    @DisplayName("Test case for saving and finding user by email")
    public void givenUser_whenFindByEmail_thenReturnUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("Password@123");
        user.setPhoneNumber("12345678900");
        user.setRole("Admin");
        user.setAge(30);
        user.setGender("Male");
        userRepo.save(user);
        Optional<User> retrievedUser = userRepo.findByEmail("johndoe@example.com");
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getEmail()).isEqualTo("johndoe@example.com");
    }

    @Test
    @DisplayName("Test case for saving and finding user by phone number")
    public void givenUser_whenFindByPhoneNumber_thenReturnUser() {
        User user = new User();
        user.setName("Jane Doe");
        user.setEmail("janedoe@example.com");
        user.setPassword("Password@123");
        user.setPhoneNumber("98765432100");
        user.setRole("User");
        user.setAge(28);
        user.setGender("Female");
        userRepo.save(user);
        Optional<User> retrievedUser = userRepo.findByPhoneNumber("98765432100");
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getPhoneNumber()).isEqualTo("98765432100");
    }
}