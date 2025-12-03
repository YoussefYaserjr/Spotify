package com.example.Spotify.dto.Request;

import lombok.Data;

@Data
public class PlaylistSongRequest {
    private Long playlistId;
    private Long songId;
    private Integer orderIndex;
}
