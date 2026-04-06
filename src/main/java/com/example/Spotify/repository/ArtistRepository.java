package com.example.Spotify.repository;

import com.example.Spotify.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByName(String name);
}
