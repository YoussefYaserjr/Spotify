package com.example.Spotify.service;

import com.example.Spotify.entity.PlaylistSong;

import java.util.List;

public interface PlaylistSongService {

    PlaylistSong addSongToPlaylist(Long playlistId, Long songId);

    void removeSongFromPlaylist(Long playlistId, Long songId);

    List<PlaylistSong> getPlaylistSongs(Long playlistId);

    void changeOrder(Long playlistId, Long songId, int newOrder);
}
