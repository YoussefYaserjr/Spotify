package com.example.Spotify.service;

import com.example.Spotify.entity.Song;

import java.util.List;

public interface SongService {

    Song createSong(Song song);

    Song getSong(Long id);

    List<Song> getAllSongs();

    List<Song> getSongsByArtist(Long artistId);

    List<Song> searchSongs(String keyword);

    void deleteSong(Long id);
}
