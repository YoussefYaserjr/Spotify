package com.example.Spotify.service;

import com.example.Spotify.dto.Request.PlaylistSongRequest;
import com.example.Spotify.dto.Response.PlaylistSongResponse;
import com.example.Spotify.entity.PlaylistSong;

import java.util.List;

public interface PlaylistSongService {
    PlaylistSongResponse addSongToPlaylist(PlaylistSongRequest request);
    public void removeSongFromPlaylist(Long playlistId, Long songId);
    PlaylistSongResponse changeOrder(Long playlistId, Long songId, int newOrder);
}