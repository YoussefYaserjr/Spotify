package com.example.Spotify.dto.Mapper;

import com.example.Spotify.dto.Request.UserRequest;
import com.example.Spotify.dto.Response.UserResponse;
import com.example.Spotify.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    User toEntity(UserRequest request);
}
