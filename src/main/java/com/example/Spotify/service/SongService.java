package com.example.Spotify.service;

import com.example.Spotify.dto.Request.SongRequest;
import com.example.Spotify.dto.Response.SongResponse;
import com.example.Spotify.entity.Song;

import java.util.List;

public interface SongService {

    /*Song createSong(Song song);

    Song getSong(Long id);

    List<Song> getAllSongs();

    List<Song> getSongsByArtist(Long artistId);

    List<Song> searchSongs(String keyword);

    void deleteSong(Long id);
*/

    SongResponse createSong(SongRequest request);

    SongResponse getSong(Long id);

    List<SongResponse> getAllSongs();

    List<SongResponse> getSongsByArtist(Long artistId);

    List<SongResponse> searchSongs(String keyword);

    void deleteSong(Long id);
}
