package com.busticketbooking.NammaOoruBus.service;

import com.busticketbooking.NammaOoruBus.dto.UserDto;
import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String id);

    void deleteUser(String id);

    UserDto viewUser(String id);

    List<UserDto> viewAllUsers();

    UserDto findUserByEmail(String email);
}