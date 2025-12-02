package com.example.Spotify.service.imp;
import com.example.Spotify.entity.Artist;
import com.example.Spotify.repository.ArtistRepository;
import com.example.Spotify.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service

public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Artist createArtist(Artist artist) {
      return artistRepository.save(artist);
    }

    @Override
    public Artist getArtistById(Long id) {
        return artistRepository.findById(id).orElseThrow(()->new RuntimeException("artist not found "));
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist updateArtist(Long id, Artist artist) {
        Artist old = getArtistById(id);
        old.setName(artist.getName());

        return artistRepository.save(old);
    }

    @Override
    public void deleteArtist(Long id) {
    artistRepository.deleteById(id);
    }
}
