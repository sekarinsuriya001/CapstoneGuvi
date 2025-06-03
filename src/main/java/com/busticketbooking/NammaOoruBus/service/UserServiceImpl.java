package com.busticketbooking.NammaOoruBus.service;

import com.busticketbooking.NammaOoruBus.dto.UserDto;
import com.busticketbooking.NammaOoruBus.entity.User;
import com.busticketbooking.NammaOoruBus.repository.UserRepo;
import com.busticketbooking.NammaOoruBus.exception.EmailPresent;
import com.busticketbooking.NammaOoruBus.exception.PhoneNumberPresent;
import com.busticketbooking.NammaOoruBus.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto addUser(UserDto userDto) {
        Optional<User> optionalUser = userRepo.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailPresent("Email Already Present");
        }
        Optional<User> optionalUser1 = userRepo.findByPhoneNumber(userDto.getPhoneNumber());
        if (optionalUser1.isPresent()) {
            throw new PhoneNumberPresent("PhoneNumber Already Present");
        }
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return modelMapper.map(userRepo.save(user), UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRole(userDto.getRole());
        // Only update password if provided and different
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        return modelMapper.map(userRepo.save(user), UserDto.class);
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepo.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepo.deleteById(id);
    }

    @Override
    public UserDto viewUser(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> viewAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return modelMapper.map(user, UserDto.class);
    }
}