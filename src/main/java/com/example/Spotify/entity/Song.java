package com.example.Spotify.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String fileUrl;
    private String coverUrl;
    private int duration; // seconds
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    @OneToMany(mappedBy = "song")
    private List<PlaylistSong> playlistSongs;
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
}