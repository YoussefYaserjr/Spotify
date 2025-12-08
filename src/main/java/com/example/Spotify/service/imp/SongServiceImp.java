package com.example.Spotify.service.imp;

import com.example.Spotify.dto.Request.SongRequest;
import com.example.Spotify.dto.Response.SongResponse;
import com.example.Spotify.entity.Artist;
import com.example.Spotify.entity.Song;
import com.example.Spotify.repository.AlbumRepository;
import com.example.Spotify.repository.ArtistRepository;
import com.example.Spotify.repository.SongRepository;
import com.example.Spotify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SongServiceImp implements SongService {



        private final SongRepository songRepository;
        private final ArtistRepository artistRepository;
        private final AlbumRepository albumRepository;

        public SongServiceImp(SongRepository songRepository,
                              ArtistRepository artistRepository,
                              AlbumRepository albumRepository) {
            this.songRepository = songRepository;
            this.artistRepository = artistRepository;
            this.albumRepository = albumRepository;
        }

        @Override
        public SongResponse createSong(SongRequest request) {

            Artist artist = artistRepository.findById(request.getArtistId())
                    .orElseThrow(() -> new RuntimeException("Artist not found"));

            Song song = Song.builder()
                    .title(request.getTitle())
                    .artist(artist)
                    .duration(request.getDuration())
                    .build();

            Song savedSong = songRepository.save(song);

            return mapToResponse(savedSong);
        }

        @Override
        public SongResponse getSong(Long id) {
            Song song = songRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Song not found"));

            return mapToResponse(song);
        }

        @Override
        public List<SongResponse> getAllSongs() {
            return songRepository.findAll()
                    .stream().map(this::mapToResponse).toList();
        }

        @Override
        public List<SongResponse> getSongsByArtist(Long artistId) {
            return songRepository.findByArtistId(artistId)
                    .stream().map(this::mapToResponse).toList();
        }

        @Override
        public List<SongResponse> searchSongs(String keyword) {
            return songRepository
                    .findByTitleContainingIgnoreCase(keyword)
                    .stream().map(this::mapToResponse).toList();
        }

        @Override
        public void deleteSong(Long id) {
            songRepository.deleteById(id);
        }

        // ----------------------
        // Mapping Helper Method
        // ----------------------
        private SongResponse mapToResponse(Song song) {
            SongResponse response = new SongResponse();
            response.setId(song.getId());
            response.setTitle(song.getTitle());
            response.setDuration(song.getDuration());

            if (song.getArtist() != null)
                response.setArtistName(song.getArtist().getName());

            if (song.getAlbum() != null)
                response.setAlbumName(song.getAlbum().getTitle());

            return response;
        }
    }


