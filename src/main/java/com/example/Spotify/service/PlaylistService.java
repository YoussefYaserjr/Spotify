package com.example.Spotify.service;

import com.example.Spotify.dto.Request.PlaylistRequest;
import com.example.Spotify.dto.Response.PlaylistResponse;
import com.example.Spotify.entity.Playlist;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface PlaylistService {

    PlaylistResponse createPlaylist(PlaylistRequest playlist);

    PlaylistResponse getPlaylist(Long id);

    List<PlaylistResponse> getUserPlaylists(Long userId);

    PlaylistResponse updatePlaylist(Long id, PlaylistRequest playlist);

    void deletePlaylist(Long id);

     List<PlaylistResponse> getAllPlaylists();
}
