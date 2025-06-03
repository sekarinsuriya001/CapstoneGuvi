package com.busticketbooking.NammaOoruBus.repository;

import com.busticketbooking.NammaOoruBus.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String number);
}