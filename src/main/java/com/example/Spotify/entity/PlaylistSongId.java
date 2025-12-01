package com.example.Spotify.entity;

import java.io.Serializable;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistSongId implements Serializable {
    private Long playlist;
    private Long song;
}