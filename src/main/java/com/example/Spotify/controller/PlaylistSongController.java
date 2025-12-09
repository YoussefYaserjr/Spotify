package com.example.Spotify.controller;

import com.example.Spotify.dto.Request.PlaylistSongRequest;
import com.example.Spotify.dto.Response.PlaylistSongResponse;
import com.example.Spotify.service.PlaylistSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist-songs")
@RequiredArgsConstructor
public class PlaylistSongController {

    private final PlaylistSongService playlistSongService;

    @PostMapping
    public ResponseEntity<PlaylistSongResponse> addSongToPlaylist(
            @RequestBody PlaylistSongRequest request) {
        return ResponseEntity.ok(playlistSongService.addSongToPlaylist(request));
    }

    @DeleteMapping("/{playlistId}/songs")
    public ResponseEntity<String> removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @RequestBody PlaylistSongRequest request) {

        playlistSongService.removeSongFromPlaylist(playlistId, request.getSongId());
        return ResponseEntity.ok("Song removed from playlist");
    }
}
