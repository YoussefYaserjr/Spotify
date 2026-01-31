package com.example.Spotify.service.imp;

import com.example.Spotify.dto.Request.ArtistRequest;
import com.example.Spotify.dto.Response.ArtistResponse;
import com.example.Spotify.entity.Artist;
import com.example.Spotify.exception.InvalidInput;
import com.example.Spotify.exception.ResourceNotFoundException;
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
    public Artist createArtist(Artist artistRequest) {

        if (artistRequest.getName() == null || artistRequest.getName().trim().isEmpty()) {
            throw new InvalidInput("Artist name cannot be empty");
        }

        return artistRepository.save(artistRequest);
    }

    @Override
    public ArtistResponse getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + id ));

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
        if (artistRequest.getName() == null || artistRequest.getName().trim().isEmpty()) {
            throw new InvalidInput("Artist name cannot be empty");
        }
        Artist existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + id));

        existingArtist.setName(artistRequest.getName());
        existingArtist.setBio(artistRequest.getBio());
        existingArtist.setCountry(artistRequest.getCountry());

        Artist updatedArtist = artistRepository.save(existingArtist);
        return convertToResponse(updatedArtist);
    }

    @Override
    public void deleteArtist(Long id) {
        // Check if artist exists before deleting
        if (!artistRepository.existsById(id)) {
            throw new ResourceNotFoundException("Artist not found with id: " + id);
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