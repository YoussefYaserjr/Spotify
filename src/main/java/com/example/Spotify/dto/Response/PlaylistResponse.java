package com.example.Spotify.dto.Response;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistResponse {
    private Long id;
    private String name;
    private String createdBy;
    private List<PlaylistSongResponse> songs;
}