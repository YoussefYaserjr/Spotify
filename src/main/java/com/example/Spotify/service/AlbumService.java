package com.example.Spotify.service;

import com.example.Spotify.dto.Request.AlbumRequest;
import com.example.Spotify.dto.Response.AlbumResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumService {

    AlbumResponse createAlbum(AlbumRequest request);

    AlbumResponse getAlbum(Long id);

    List<AlbumResponse> getAllAlbums();

    List<AlbumResponse> getAlbumsByArtist(Long artistId);
    List<AlbumResponse> searchAlbums(String keyword);

    void deleteAlbum(Long id);
}