package com.example.Spotify.service.imp;

import com.example.Spotify.dto.Request.AlbumRequest;
import com.example.Spotify.dto.Response.AlbumResponse;
import com.example.Spotify.entity.Album;
import com.example.Spotify.entity.Artist;
import com.example.Spotify.exception.InvalidInput;
import com.example.Spotify.exception.ResourceNotFoundException;
import com.example.Spotify.repository.AlbumRepository;
import com.example.Spotify.repository.ArtistRepository;
import com.example.Spotify.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    @Override
    public AlbumResponse createAlbum(AlbumRequest request) {

        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        Album album = Album.builder()
                .title(request.getTitle())

                .artist(artist)
                .build();

        Album saved = albumRepository.save(album);

        return mapToResponse(saved);
    }

    @Override
    public AlbumResponse getAlbum(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found"));

        return mapToResponse(album);
    }

    @Override
    public List<AlbumResponse> getAllAlbums() {
        return albumRepository.findAll()
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<AlbumResponse> getAlbumsByArtist(Long artistId) {
        if (!artistRepository.existsById(artistId)) {
            throw new ResourceNotFoundException("Artist not found with id: " + artistId);
        }
        if (albumRepository.findByArtistId(artistId).stream().map(this::mapToResponse).toList().isEmpty()) {
            throw new InvalidInput("Artist doesn't have Albums ");
        }
        return albumRepository.findByArtistId(artistId)
                .stream().map(this::mapToResponse).toList();

    }


    @Override
    public List<AlbumResponse> searchAlbums(String keyword) {
        return albumRepository.searchAlbums(keyword)
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }


    // 🔹 Mapping Method
    private AlbumResponse mapToResponse(Album album) {
        AlbumResponse response = new AlbumResponse();
        response.setId(album.getId());
        response.setTitle(album.getTitle());
        response.setCoverImage(album.getCoverImage());

        if (album.getArtist() != null)
            response.setArtistName(album.getArtist().getName());

        return response;
    }
}
