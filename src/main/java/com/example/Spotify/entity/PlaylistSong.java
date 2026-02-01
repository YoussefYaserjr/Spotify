package com.example.Spotify.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "playlist_songs")
@IdClass(PlaylistSongId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistSong {

    @Id  // Add @Id here
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @Id  // Add @Id here
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Column(name = "order_index")
    private Integer orderIndex;

}