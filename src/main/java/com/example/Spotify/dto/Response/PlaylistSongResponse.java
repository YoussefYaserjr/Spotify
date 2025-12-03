package com.example.Spotify.dto.Response;

import lombok.Data;

@Data
public class PlaylistSongResponse {
    private Long songId;
    private String title;
    private String artistName;
    private Integer orderIndex;
}
