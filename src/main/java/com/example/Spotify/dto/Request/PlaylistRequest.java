package com.example.Spotify.dto.Request;

import lombok.Data;

@Data
public class PlaylistRequest {
    private String name;
    private Long userId;
}