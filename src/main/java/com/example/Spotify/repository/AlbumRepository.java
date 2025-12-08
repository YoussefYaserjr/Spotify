package com.example.Spotify.repository;

import com.example.Spotify.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByArtistId(Long artistId);

    List<Album> findByTitleContainingIgnoreCase(String title);
}
