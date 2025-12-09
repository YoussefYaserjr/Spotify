package com.example.Spotify.controller;

import com.example.Spotify.dto.Request.ArtistRequest;
import com.example.Spotify.dto.Response.ArtistResponse;
import com.example.Spotify.service.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    // Create a new artist
    @PostMapping
    public ResponseEntity<ArtistResponse> createArtist(@RequestBody ArtistRequest artistRequest) {
        ArtistResponse createdArtist = artistService.createArtist(artistRequest);
        return new ResponseEntity<>(createdArtist, HttpStatus.CREATED);
    }

    // Get artist by ID
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable Long id) {
        ArtistResponse artist = artistService.getArtistById(id);
        return ResponseEntity.ok(artist);
    }

    // Get all artists
    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        List<ArtistResponse> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    // Update artist
    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponse> updateArtist(
            @PathVariable Long id,
            @RequestBody ArtistRequest artistRequest) {
        ArtistResponse updatedArtist = artistService.updateArtist(id, artistRequest);
        return ResponseEntity.ok(updatedArtist);
    }

    // Delete artist
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}