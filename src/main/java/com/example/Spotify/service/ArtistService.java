package com.example.Spotify.service;

import com.example.Spotify.dto.Request.ArtistRequest;
import com.example.Spotify.dto.Response.ArtistResponse;
import com.example.Spotify.entity.Artist;

import java.util.List;


public interface ArtistService {

    Artist createArtist(Artist  artist);

    ArtistResponse getArtistById(Long id);

    List<ArtistResponse> getAllArtists();

    ArtistResponse updateArtist(Long id, ArtistRequest artist);

    void deleteArtist(Long id);
}
