package com.example.Spotify.controller;

import com.example.Spotify.dto.Request.PlaylistRequest;
import com.example.Spotify.dto.Response.PlaylistResponse;
import com.example.Spotify.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<PlaylistResponse> createPlaylist(@RequestBody PlaylistRequest request) {
        return ResponseEntity.ok(playlistService.createPlaylist(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponse> getPlaylist(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.getPlaylist(id));
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponse>> getAllPlaylists() {
        return ResponseEntity.ok(playlistService.getAllPlaylists());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.ok("Playlist deleted");
    }
}
