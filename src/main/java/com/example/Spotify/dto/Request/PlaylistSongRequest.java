package com.example.Spotify.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistSongRequest {
    private Long playlistId;
    private Long songId;
    private Integer orderIndex;
}
