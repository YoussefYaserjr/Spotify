package com.example.Spotify.repository;

import com.example.Spotify.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByArtistId(Long artistId);
    List<Song> findByArtist_Name(String artistName);

    List<Song> findByTitleContainingIgnoreCase(String title);

    List<Song> findByTitleContainingIgnoreCaseOrArtist_NameContainingIgnoreCase(
            String title, String artistName);



}