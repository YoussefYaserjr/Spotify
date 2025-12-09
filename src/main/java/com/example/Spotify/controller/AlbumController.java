package com.example.Spotify.controller;

import com.example.Spotify.dto.Request.AlbumRequest;
import com.example.Spotify.dto.Response.AlbumResponse;
import com.example.Spotify.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping
    public ResponseEntity<AlbumResponse> createAlbum(@RequestBody AlbumRequest request) {
        return ResponseEntity.ok(albumService.createAlbum(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse> getAlbum(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.getAlbum(id));
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponse>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<AlbumResponse>> getAlbumsByArtist(@PathVariable Long artistId) {
        return ResponseEntity.ok(albumService.getAlbumsByArtist(artistId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AlbumResponse>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(albumService.searchAlbums(keyword));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.ok("Album deleted");
    }
}
