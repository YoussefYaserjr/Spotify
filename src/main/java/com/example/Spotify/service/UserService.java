package com.example.Spotify.service;


import com.example.Spotify.dto.Request.UserRequest;
import com.example.Spotify.dto.Response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse registerUser(UserRequest userRequest);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long id, UserRequest userRequest);
    UserResponse getUserByEmail(String email);
    void deleteUser(Long id);
}