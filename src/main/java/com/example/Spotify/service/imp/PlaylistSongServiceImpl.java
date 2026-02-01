package com.example.Spotify.service.imp;

import com.example.Spotify.dto.Request.PlaylistSongRequest;
import com.example.Spotify.dto.Response.PlaylistSongResponse;
import com.example.Spotify.entity.Playlist;
import com.example.Spotify.entity.PlaylistSong;
import com.example.Spotify.entity.PlaylistSongId;
import com.example.Spotify.entity.Song;
import com.example.Spotify.exception.InvalidInput;
import com.example.Spotify.exception.ResourceNotFoundException;
import com.example.Spotify.repository.PlaylistRepository;
import com.example.Spotify.repository.PlaylistSongRepository;
import com.example.Spotify.repository.SongRepository;
import com.example.Spotify.service.PlaylistSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistSongServiceImpl implements PlaylistSongService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final PlaylistSongRepository playlistSongRepository;

    @Override
    @Transactional
    public PlaylistSongResponse addSongToPlaylist(PlaylistSongRequest request) {
        // Fetch playlist with songs
        Playlist playlist = playlistRepository.findByIdWithSongs(request.getPlaylistId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Playlist not found with id: " + request.getPlaylistId()));

        // Fetch song
        Song song = songRepository.findById(request.getSongId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Song not found with id: " + request.getSongId()));

        // Check if song already exists in playlist
        PlaylistSongId playlistSongId = new PlaylistSongId();
        playlistSongId.setPlaylist(request.getPlaylistId());
        playlistSongId.setSong(request.getSongId());

        if (playlistSongRepository.existsById(playlistSongId)) {
            throw new InvalidInput("Song is already in the playlist");
        }

        // Determine order index
        int orderIndex;
        if (request.getOrderIndex() != null) {
            orderIndex = request.getOrderIndex();
            // Shift existing songs if needed
            shiftOrderIndexes(playlist, orderIndex);
        } else {
            // Add to end
            orderIndex = playlist.getPlaylistSongs().isEmpty()
                    ? 0
                    : playlist.getPlaylistSongs().stream()
                    .mapToInt(PlaylistSong::getOrderIndex)
                    .max()
                    .orElse(-1) + 1;
        }

        // Create and save PlaylistSong
        PlaylistSong playlistSong = new PlaylistSong();
        playlistSong.setPlaylist(playlist);
        playlistSong.setSong(song);
        playlistSong.setOrderIndex(orderIndex);

        playlistSongRepository.save(playlistSong);

        // Build response
        return PlaylistSongResponse.builder()
                .playlistId(playlist.getId())
                .playlistName(playlist.getName())
                .songId(song.getId())
                .songTitle(song.getTitle())
                .artistName(song.getArtist() != null ? song.getArtist().getName() : null)
                .albumName(song.getAlbum() != null ? song.getAlbum().getTitle() : null)
                .orderIndex(orderIndex)
                .totalSongs(playlist.getPlaylistSongs().size() + 1)
                .message("Song added successfully to playlist")
                .build();
    }

    @Override
    @Transactional
    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        // Verify playlist exists
        Playlist playlist = playlistRepository.findByIdWithSongs(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Playlist not found with id: " + playlistId));

        // Create composite key
        PlaylistSongId playlistSongId = new PlaylistSongId();
        playlistSongId.setPlaylist(playlistId);
        playlistSongId.setSong(songId);

        // Find and delete
        PlaylistSong playlistSong = playlistSongRepository.findById(playlistSongId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Song not found in playlist"));

        int removedIndex = playlistSong.getOrderIndex();

        playlistSongRepository.delete(playlistSong);

        // Reorder remaining songs to fill the gap
        reorderAfterRemoval(playlist, removedIndex);
    }

    @Override
    @Transactional
    public PlaylistSongResponse changeOrder(Long playlistId, Long songId, int newOrder) {
        // Fetch playlist with songs
        Playlist playlist = playlistRepository.findByIdWithSongs(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Playlist not found with id: " + playlistId));

        // Create composite key
        PlaylistSongId playlistSongId = new PlaylistSongId();
        playlistSongId.setPlaylist(playlistId);
        playlistSongId.setSong(songId);

        // Find the playlist song
        PlaylistSong playlistSong = playlistSongRepository.findById(playlistSongId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Song not found in playlist"));

        int oldIndex = playlistSong.getOrderIndex();

        if (oldIndex == newOrder) {
            // No change needed
            Song song = playlistSong.getSong();
            return buildResponse(playlist, song, newOrder);
        }

        // Validate new order
        if (newOrder < 0 || newOrder >= playlist.getPlaylistSongs().size()) {
            throw new IllegalArgumentException("Invalid order index: " + newOrder);
        }

        // Remove from current position
        playlist.getPlaylistSongs().remove(playlistSong);

        // Shift other songs
        if (newOrder < oldIndex) {
            // Moving up - shift songs down
            for (PlaylistSong ps : playlist.getPlaylistSongs()) {
                if (ps.getOrderIndex() >= newOrder && ps.getOrderIndex() < oldIndex) {
                    ps.setOrderIndex(ps.getOrderIndex() + 1);
                    playlistSongRepository.save(ps);
                }
            }
        } else {
            // Moving down - shift songs up
            for (PlaylistSong ps : playlist.getPlaylistSongs()) {
                if (ps.getOrderIndex() > oldIndex && ps.getOrderIndex() <= newOrder) {
                    ps.setOrderIndex(ps.getOrderIndex() - 1);
                    playlistSongRepository.save(ps);
                }
            }
        }

        // Update the song's position
        playlistSong.setOrderIndex(newOrder);
        playlistSongRepository.save(playlistSong);

        Song song = playlistSong.getSong();
        return buildResponse(playlist, song, newOrder);
    }

    private void shiftOrderIndexes(Playlist playlist, int fromIndex) {
        for (PlaylistSong ps : playlist.getPlaylistSongs()) {
            if (ps.getOrderIndex() >= fromIndex) {
                ps.setOrderIndex(ps.getOrderIndex() + 1);
                playlistSongRepository.save(ps);
            }
        }
    }

    private void reorderAfterRemoval(Playlist playlist, int removedIndex) {
        for (PlaylistSong ps : playlist.getPlaylistSongs()) {
            if (ps.getOrderIndex() > removedIndex) {
                ps.setOrderIndex(ps.getOrderIndex() - 1);
                playlistSongRepository.save(ps);
            }
        }
    }

    private PlaylistSongResponse buildResponse(Playlist playlist, Song song, int orderIndex) {
        return PlaylistSongResponse.builder()
                .playlistId(playlist.getId())
                .playlistName(playlist.getName())
                .songId(song.getId())
                .songTitle(song.getTitle())
                .artistName(song.getArtist() != null ? song.getArtist().getName() : null)
                .albumName(song.getAlbum() != null ? song.getAlbum().getTitle() : null)
                .orderIndex(orderIndex)
                .totalSongs(playlist.getPlaylistSongs().size())
                .message("Order changed successfully")
                .build();
    }
}