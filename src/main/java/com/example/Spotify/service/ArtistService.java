package com.example.Spotify.service;

import com.example.Spotify.entity.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistService {

    Artist createArtist(Artist artist);

    Artist getArtistById(Long id);

    List<Artist> getAllArtists();

    Artist updateArtist(Long id, Artist artist);

    void deleteArtist(Long id);
}
