package com.example.Spotify.controller;

import com.example.Spotify.dto.Request.ArtistRequest;
import com.example.Spotify.dto.Response.ArtistResponse;
import com.example.Spotify.entity.Artist;
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

    @PostMapping// ✅
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        Artist savedArtist = artistService.createArtist(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArtist);
    }


    @GetMapping("/{id}")//  ✅
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable Long id) {
        ArtistResponse artist = artistService.getArtistById(id);
        return ResponseEntity.ok(artist);
    }


    @GetMapping//   ✅
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        List<ArtistResponse> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    // Update artist
    @PutMapping("/{id}")//✅
    public ResponseEntity<String> updateArtist(
            @PathVariable Long id,
            @RequestBody ArtistRequest artistRequest) {
        ArtistResponse updatedArtist = artistService.updateArtist(id, artistRequest);

        return ResponseEntity.ok("Artist updated successfully");

        //return ResponseEntity.ok(updatedArtist);
        /*return ResponseEntity.ok()
                .header("X-Message", "Artist updated successfully")
                .header("X-Status", "success")
                .body(updatedArtist);*/
    }

    // Delete artist
    @DeleteMapping("/{id}")//✅
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}