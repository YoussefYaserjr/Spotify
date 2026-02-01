package com.example.Spotify.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistSongId implements Serializable {
    // These MUST match the @Id field names in PlaylistSong
    private Long playlist;  // Field name in PlaylistSong: private Playlist playlist;
    private Long song;      // Field name in PlaylistSong: private Song song;
}

