package com.example.Spotify.controller;

import com.example.Spotify.dto.Request.PlaylistSongRequest;
import com.example.Spotify.dto.Response.PlaylistSongResponse;
import com.example.Spotify.service.PlaylistSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistSongController {

    private final PlaylistSongService playlistSongService;

    @PostMapping("/songs")
    public ResponseEntity<PlaylistSongResponse> addSongToPlaylist(
            @RequestBody PlaylistSongRequest request) {
        return ResponseEntity.ok(playlistSongService.addSongToPlaylist(request));
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        playlistSongService.removeSongFromPlaylist(playlistId, songId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{playlistId}/songs/{songId}/order")
    public ResponseEntity<PlaylistSongResponse> changeOrder(
            @PathVariable Long playlistId,
            @PathVariable Long songId,
            @RequestParam int newOrder) {
        return ResponseEntity.ok(
                playlistSongService.changeOrder(playlistId, songId, newOrder));
    }
}