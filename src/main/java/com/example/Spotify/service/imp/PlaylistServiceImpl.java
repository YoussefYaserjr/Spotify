package com.example.Spotify.service.imp;

import com.example.Spotify.dto.Request.PlaylistRequest;
import com.example.Spotify.dto.Response.PlaylistResponse;
import com.example.Spotify.dto.Response.PlaylistSongResponse;
import com.example.Spotify.entity.Playlist;
import com.example.Spotify.entity.User;
import com.example.Spotify.exception.InvalidInput;
import com.example.Spotify.exception.ResourceNotFoundException;
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
            throw new InvalidInput("Playlist name cannot be empty");
        }

        // Validate user exists
        User user = userRepository.findById(playlistRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + playlistRequest.getUserId()));

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
        Playlist playlist = playlistRepository.findByIdWithSongs(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id: " + id));

        return convertToResponse(playlist);
    }
    @Override
    public List<PlaylistResponse> getAllPlaylists() {
        List<Playlist> playlists = playlistRepository.findAllWithSongs();
        return playlists.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<PlaylistResponse> getUserPlaylists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        List<Playlist> playlists = playlistRepository.findByUserId(userId);
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
        //response.setCreatedBy(playlist.getUser().getUsername());


        if (playlist.getUser() != null) {
            response.setCreatedBy(playlist.getUser().getUsername());
        } else {
            response.setCreatedBy("Unknown");
        }

        List<PlaylistSongResponse> songs = playlist.getPlaylistSongs()
                .stream()
                .map(ps -> {
                    PlaylistSongResponse r = new PlaylistSongResponse();
                    r.setOrderIndex(ps.getOrderIndex());
                    r.setSongId(ps.getSong().getId());
                    r.setTitle(ps.getSong().getTitle());
                    r.setArtistName(ps.getSong().getArtist().getName());
                    return r;
                })
                .toList();

        response.setSongs(songs);


        return response;
    }


}