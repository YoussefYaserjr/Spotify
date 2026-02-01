package com.example.Spotify.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistSongResponse {
    private Long playlistId;
    private String playlistName;
    private Long songId;
    private String songTitle;
    private String artistName;
    private String albumName;
    private Integer orderIndex;
    private Integer totalSongs;
    private String message;
}