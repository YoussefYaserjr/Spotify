package com.example.Spotify.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(PlaylistSongId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "playlist_songs")
public class PlaylistSong {
    @Id
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;
    @Id
    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;
    @Column(name = "order_index")
    private Integer orderIndex;
}
