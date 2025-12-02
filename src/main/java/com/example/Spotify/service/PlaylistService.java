package com.example.Spotify.service;

import com.example.Spotify.entity.Playlist;

import java.util.List;

public interface PlaylistService {

    Playlist createPlaylist(Playlist playlist);

    Playlist getPlaylist(Long id);

    List<Playlist> getUserPlaylists(Long userId);

    Playlist updatePlaylist(Long id, Playlist playlist);

    void deletePlaylist(Long id);
}
