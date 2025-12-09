package com.example.Spotify.controller;

import com.example.Spotify.dto.Request.SongRequest;
import com.example.Spotify.dto.Response.SongResponse;
import com.example.Spotify.entity.Song;
import com.example.Spotify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping
    public ResponseEntity<SongResponse> createSong(@RequestBody SongRequest song) {
        return ResponseEntity.ok(songService.createSong(song));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> getSong(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSong(id));
    }

    @GetMapping
    public ResponseEntity<List<SongResponse>> getAllSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<SongResponse>> getSongsByArtist(@PathVariable Long artistId) {
        return ResponseEntity.ok(songService.getSongsByArtist(artistId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SongResponse>> searchSongs(@RequestParam String keyword) {
        return ResponseEntity.ok(songService.searchSongs(keyword));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.ok("Song deleted successfully");
    }
}
