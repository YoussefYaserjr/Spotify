package com.example.Spotify.service.imp;

import com.example.Spotify.dto.Request.PlaylistRequest;
import com.example.Spotify.dto.Response.PlaylistResponse;
import com.example.Spotify.dto.Response.PlaylistSongResponse;
import com.example.Spotify.entity.Playlist;
import com.example.Spotify.entity.User;
import com.example.Spotify.repository.PlaylistRepository;
import com.example.Spotify.repository.UserRepository;
import com.example.Spotify.service.PlaylistService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PlaylistResponse createPlaylist(PlaylistRequest playlistRequest) {
        // Validate playlist name
        if (playlistRequest.getName() == null || playlistRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Playlist name cannot be empty");
        }

        // Validate user exists
        User user = userRepository.findById(playlistRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + playlistRequest.getUserId()));

        // Create playlist entity
        Playlist playlist = new Playlist();
        playlist.setName(playlistRequest.getName());
        playlist.setUser(user);

        // Save playlist
        Playlist savedPlaylist = playlistRepository.save(playlist);

        // Convert to response and return
        return convertToResponse(savedPlaylist);
    }

    @Override
    public PlaylistResponse getPlaylist(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + id));

        return convertToResponse(playlist);
    }
    @Override
    public List<PlaylistResponse> getAllPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<PlaylistResponse> getUserPlaylists(Long userId) {
        // Validate user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        // Get all playlists for the user
        List<Playlist> playlists = playlistRepository.findByUserId(userId);

        // Convert to response list
        return playlists.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PlaylistResponse updatePlaylist(Long id, PlaylistRequest playlistRequest) {
        // Validate playlist exists
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + id));

        // Validate new name
        if (playlistRequest.getName() == null || playlistRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Playlist name cannot be empty");
        }

        // Update playlist name
        playlist.setName(playlistRequest.getName());

        // If userId is provided and different, update user
        if (playlistRequest.getUserId() != null &&
                (playlist.getUser() == null || !playlist.getUser().getId().equals(playlistRequest.getUserId()))) {
            User user = userRepository.findById(playlistRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + playlistRequest.getUserId()));
            playlist.setUser(user);
        }

        // Save updated playlist
        Playlist updatedPlaylist = playlistRepository.save(playlist);

        return convertToResponse(updatedPlaylist);
    }

    @Override
    public void deletePlaylist(Long id) {
        // Check if playlist exists
        if (!playlistRepository.existsById(id)) {
            throw new RuntimeException("Playlist not found with id: " + id);
        }

        playlistRepository.deleteById(id);
    }



    // Helper method to convert Playlist entity to PlaylistResponse
    private PlaylistResponse convertToResponse(Playlist playlist) {
        PlaylistResponse response = new PlaylistResponse();
        response.setId(playlist.getId());
        response.setName(playlist.getName());

        // Set createdBy - assuming User entity has getName() method
        if (playlist.getUser() != null) {
            response.setCreatedBy(playlist.getUser().getUsername());
        } else {
            response.setCreatedBy("Unknown");
        }

        // Convert songs to PlaylistSongResponse
        // Assuming Playlist has getSongs() method that returns List<Song> or similar
        // and you have a method to convert Song to PlaylistSongResponse
        response.setSongs(convertSongsToResponse(playlist.getPlaylistSongs()));

        return response;
    }

    // Helper method to convert songs
    private List<PlaylistSongResponse> convertSongsToResponse(List<?> songs) {
        if (songs == null || songs.isEmpty()) {
            return new ArrayList<>();
        }

        // You need to implement this based on your Song entity structure
        // Example:
        return songs.stream()
                .map(song -> {
                    PlaylistSongResponse songResponse = new PlaylistSongResponse();
                    // Set song properties here
                    // songResponse.setId(song.getId());
                    // songResponse.setTitle(song.getTitle());
                    // etc.
                    return songResponse;
                })
                .collect(Collectors.toList());
    }
}