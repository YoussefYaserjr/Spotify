package com.example.Spotify.service.imp;

import com.example.Spotify.entity.Playlist;
import com.example.Spotify.entity.PlaylistSong;
import com.example.Spotify.entity.Song;
import com.example.Spotify.repository.PlaylistRepository;
import com.example.Spotify.repository.PlaylistSongRepository;
import com.example.Spotify.repository.SongRepository;
import com.example.Spotify.service.PlaylistSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PlaylistSongServiceImpl implements PlaylistSongService {

    private final PlaylistSongRepository repo;
    private final PlaylistRepository playlistRepo;
    private final SongRepository songRepo;

    public PlaylistSongServiceImpl(PlaylistSongRepository repo, PlaylistRepository playlistRepo, SongRepository songRepo) {
        this.repo = repo;
        this.playlistRepo = playlistRepo;
        this.songRepo = songRepo;
    }

    @Override
    public PlaylistSong addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepo.findById(playlistId).orElseThrow(()->new RuntimeException("Playlist not found"));
        Song song=songRepo.findById(songId).orElseThrow(()->new RuntimeException("Song not found"));
        int nextOrder = repo.findByPlaylistIdOrderByOrderIndex(playlistId).size();
            PlaylistSong ps=new PlaylistSong(playlist,song,nextOrder);
           return repo.save(ps);
    }

    @Override
    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        repo.deleteByPlaylistIdAndSongId(playlistId, songId);

    }

    @Override
    public List<PlaylistSong> getPlaylistSongs(Long playlistId) {
        return repo.findByPlaylistIdOrderByOrderIndex(playlistId);

    }

    @Override
    public void changeOrder(Long playlistId, Long songId, int newOrder) {
        List<PlaylistSong> songs = repo.findByPlaylistIdOrderByOrderIndex(playlistId);

        // Find the song to move
        PlaylistSong songToMove = songs.stream()
                .filter(ps -> ps.getSong().getId().equals(songId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Song not found in playlist"));

        int oldOrder = songToMove.getOrderIndex();

        // Validate new order
        if (newOrder < 0 || newOrder >= songs.size()) {
            throw new IllegalArgumentException("Invalid new order position");
        }

        // If position hasn't changed, do nothing
        if (oldOrder == newOrder) {
            return;
        }

        // Shift songs between old and new positions
        if (oldOrder < newOrder) {
            // Moving down: shift songs up (decrease their index)
            for (PlaylistSong ps : songs) {
                int currentIndex = ps.getOrderIndex();
                if (currentIndex > oldOrder && currentIndex <= newOrder) {
                    ps.setOrderIndex(currentIndex - 1);
                }
            }
        } else {
            // Moving up: shift songs down (increase their index)
            for (PlaylistSong ps : songs) {
                int currentIndex = ps.getOrderIndex();
                if (currentIndex >= newOrder && currentIndex < oldOrder) {
                    ps.setOrderIndex(currentIndex + 1);
                }
            }
        }

        // Set the new position for the song being moved
        songToMove.setOrderIndex(newOrder);

        // Save all changes
        repo.saveAll(songs);
    }


}
