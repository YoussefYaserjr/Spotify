package com.example.Spotify.service.imp;

import com.example.Spotify.dto.Request.ArtistRequest;
import com.example.Spotify.dto.Response.ArtistResponse;
import com.example.Spotify.entity.Artist;
import com.example.Spotify.repository.ArtistRepository;
import com.example.Spotify.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public ArtistResponse createArtist(ArtistRequest artistRequest) {
        // Basic validation
        if (artistRequest.getName() == null || artistRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Artist name cannot be empty");
        }

        // Convert ArtistRequest to Artist entity
        Artist artist = new Artist();
        artist.setName(artistRequest.getName());

        // Save entity
        Artist savedArtist = artistRepository.save(artist);

        // Convert to ArtistResponse and return
        return convertToResponse(savedArtist);
    }

    @Override
    public ArtistResponse getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));

        return convertToResponse(artist);
    }

    @Override
    public List<ArtistResponse> getAllArtists() {
        List<Artist> artists = artistRepository.findAll();

        return artists.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistResponse updateArtist(Long id, ArtistRequest artistRequest) {
        // Basic validation
        if (artistRequest.getName() == null || artistRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Artist name cannot be empty");
        }

        // Get existing artist
        Artist existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));

        // Update fields
        existingArtist.setName(artistRequest.getName());

        // Save updated artist
        Artist updatedArtist = artistRepository.save(existingArtist);

        // Convert to response
        return convertToResponse(updatedArtist);
    }

    @Override
    public void deleteArtist(Long id) {
        // Check if artist exists before deleting
        if (!artistRepository.existsById(id)) {
            throw new RuntimeException("Artist not found with id: " + id);
        }
        artistRepository.deleteById(id);
    }

    // Helper method to convert Entity to Response DTO
    private ArtistResponse convertToResponse(Artist artist) {
        ArtistResponse response = new ArtistResponse();
        response.setId(artist.getId());
        response.setName(artist.getName());
        response.setCountry(artist.getCountry());
        return response;
    }
}