package com.example.Spotify.service.imp;

import com.example.Spotify.dto.Request.PlaylistSongRequest;
import com.example.Spotify.dto.Response.PlaylistSongResponse;
import com.example.Spotify.entity.Playlist;
import com.example.Spotify.entity.PlaylistSong;
import com.example.Spotify.entity.Song;
import com.example.Spotify.repository.PlaylistSongRepository;
import com.example.Spotify.repository.PlaylistRepository;
import com.example.Spotify.repository.SongRepository;
import com.example.Spotify.service.PlaylistSongService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistSongServiceImpl implements PlaylistSongService {

    private final PlaylistSongRepository repo;
    private final PlaylistRepository playlistRepo;
    private final SongRepository songRepo;

    public PlaylistSongServiceImpl(
            PlaylistSongRepository repo,
            PlaylistRepository playlistRepo,
            SongRepository songRepo) {
        this.repo = repo;
        this.playlistRepo = playlistRepo;
        this.songRepo = songRepo;
    }

    @Override
    public PlaylistSongResponse addSongToPlaylist(PlaylistSongRequest request) {
        // Validate request
        if (request.getPlaylistId() == null) {
            throw new IllegalArgumentException("Playlist ID is required");
        }
        if (request.getSongId() == null) {
            throw new IllegalArgumentException("Song ID is required");
        }

        // Check if playlist exists
        Playlist playlist = playlistRepo.findById(request.getPlaylistId())
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + request.getPlaylistId()));

        // Check if song exists
        Song song = songRepo.findById(request.getSongId())
                .orElseThrow(() -> new RuntimeException("Song not found with id: " + request.getSongId()));

        // Check if song is already in playlist
        if (repo.existsByPlaylistIdAndSongId(request.getPlaylistId(), request.getSongId())) {
            throw new RuntimeException("Song is already in the playlist");
        }

        // Get next order index
        int nextOrder = repo.findByPlaylistIdOrderByOrderIndex(request.getPlaylistId()).size();

        // If orderIndex is provided in request, use it (with validation)
        if (request.getOrderIndex() != null) {
            List<PlaylistSong> existingSongs = repo.findByPlaylistIdOrderByOrderIndex(request.getPlaylistId());
            if (request.getOrderIndex() < 0 || request.getOrderIndex() > existingSongs.size()) {
                throw new IllegalArgumentException("Invalid order index. Must be between 0 and " + existingSongs.size());
            }
            nextOrder = request.getOrderIndex();

            // Shift existing songs if needed
            if (nextOrder < existingSongs.size()) {
                for (PlaylistSong ps : existingSongs) {
                    if (ps.getOrderIndex() >= nextOrder) {
                        ps.setOrderIndex(ps.getOrderIndex() + 1);
                    }
                }
                repo.saveAll(existingSongs);
            }
        }

        // Create PlaylistSong
        PlaylistSong playlistSong = new PlaylistSong(playlist, song, nextOrder);

        // Save
        PlaylistSong savedPlaylistSong = repo.save(playlistSong);

        // Convert to response and return
        return convertToResponse(savedPlaylistSong);
    }

    @Override
    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        // Check if playlist exists
        if (!playlistRepo.existsById(playlistId)) {
            throw new RuntimeException("Playlist not found with id: " + playlistId);
        }

        // Check if song exists
        if (!songRepo.existsById(songId)) {
            throw new RuntimeException("Song not found with id: " + songId);
        }

        // Check if the song exists in playlist
        if (!repo.existsByPlaylistIdAndSongId(playlistId, songId)) {
            throw new RuntimeException("Song not found in playlist");
        }

        // Get the order index of the song being removed
        List<PlaylistSong> songs = repo.findByPlaylistIdOrderByOrderIndex(playlistId);
        PlaylistSong songToRemove = songs.stream()
                .filter(ps -> ps.getSong().getId().equals(songId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Song not found in playlist"));

        int removedOrder = songToRemove.getOrderIndex();

        // Remove the song
        repo.deleteByPlaylistIdAndSongId(playlistId, songId);

        // Reorder remaining songs
        List<PlaylistSong> remainingSongs = repo.findByPlaylistIdOrderByOrderIndex(playlistId);
        for (PlaylistSong ps : remainingSongs) {
            if (ps.getOrderIndex() > removedOrder) {
                ps.setOrderIndex(ps.getOrderIndex() - 1);
                repo.save(ps);
            }
        }
    }

    @Override
    public List<PlaylistSongResponse> getPlaylistSongs(Long playlistId) {
        // Check if playlist exists
        if (!playlistRepo.existsById(playlistId)) {
            throw new RuntimeException("Playlist not found with id: " + playlistId);
        }

        List<PlaylistSong> playlistSongs = repo.findByPlaylistIdOrderByOrderIndex(playlistId);

        return playlistSongs.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PlaylistSongResponse changeOrder(Long playlistId, Long songId, int newOrder) {
        List<PlaylistSong> songs = repo.findByPlaylistIdOrderByOrderIndex(playlistId);

        // Find the song to move
        PlaylistSong songToMove = songs.stream()
                .filter(ps -> ps.getSong().getId().equals(songId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Song not found in playlist"));

        int oldOrder = songToMove.getOrderIndex();

        // Validate new order
        if (newOrder < 0 || newOrder >= songs.size()) {
            throw new IllegalArgumentException("Invalid new order position. Must be between 0 and " + (songs.size() - 1));
        }

        // If position hasn't changed, return current
        if (oldOrder == newOrder) {
            return convertToResponse(songToMove);
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

        // Return updated response
        return convertToResponse(songToMove);
    }

    // Helper method to convert PlaylistSong entity to PlaylistSongResponse
    private PlaylistSongResponse convertToResponse(PlaylistSong playlistSong) {
        PlaylistSongResponse response = new PlaylistSongResponse();
        response.setSongId(playlistSong.getSong().getId());
        response.setTitle(playlistSong.getSong().getTitle()); // Assuming Song has getTitle()

        // Assuming Song has getArtist() and Artist has getName()
        if (playlistSong.getSong().getArtist() != null) {
            response.setArtistName(playlistSong.getSong().getArtist().getName());
        } else {
            response.setArtistName("Unknown");
        }

        response.setOrderIndex(playlistSong.getOrderIndex());
        return response;
    }
}