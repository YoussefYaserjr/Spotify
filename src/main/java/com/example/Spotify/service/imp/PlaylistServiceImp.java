package com.example.Spotify.service.imp;

import com.example.Spotify.entity.Playlist;
import com.example.Spotify.repository.PlaylistRepository;
import com.example.Spotify.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service

public class PlaylistServiceImp implements PlaylistService {
    private final PlaylistRepository playlistRepository;

    public PlaylistServiceImp(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist getPlaylist(Long id) {
        return playlistRepository.findById(id).orElseThrow(()->new RuntimeException("Playlist not found"));
    }

    @Override
    public List<Playlist> getUserPlaylists(Long userId) {
        return playlistRepository.findByUserId(userId);
    }

    @Override
    public Playlist updatePlaylist(Long id, Playlist playlist) {
        Playlist oldPlaylist = getPlaylist(id);
        oldPlaylist.setName(playlist.getName());
        return playlistRepository.save(oldPlaylist);
    }

    @Override
    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }
}
