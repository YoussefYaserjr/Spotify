package com.example.Spotify.service.imp;

import com.example.Spotify.entity.Song;
import com.example.Spotify.repository.SongRepository;
import com.example.Spotify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SongServiceImp implements SongService {

    private final  SongRepository songRepository;

    public SongServiceImp(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song createSong(Song song) {
     return songRepository.save(song);
    }

    @Override
    public Song getSong(Long id) {
        return songRepository.findById(id).orElseThrow(()->new RuntimeException("Song not found"));
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public List<Song> getSongsByArtist(Long artistId) {
        return songRepository.findByArtistId(artistId);
    }

    @Override
    public List<Song> searchSongs(String keyword) {
        return songRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
}
