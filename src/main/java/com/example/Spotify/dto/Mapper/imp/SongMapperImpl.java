package com.example.Spotify.dto.Mapper.imp;

import com.example.Spotify.dto.Mapper.SongMapper;
import com.example.Spotify.dto.Request.SongRequest;
import com.example.Spotify.dto.Response.SongResponse;
import com.example.Spotify.entity.Song;
import org.springframework.stereotype.Component;

@Component
public class SongMapperImpl implements SongMapper {

    @Override
    public SongResponse toResponse(Song song) {
        if(song==null)return null;
      /*  return SongResponse.builder()
                .id(song.getId())
                .title(song.getTitle())
                .artistName(song.getArtist().getName())
                .albumName(song.getAlbum().getTitle())
                .duration(song.getDuration()).build();*/
        SongResponse songResponse = new SongResponse();
        songResponse.setId(song.getId());
        songResponse.setTitle(song.getTitle());
        songResponse.setArtistName(song.getArtist().getName());
        songResponse.setDuration(song.getDuration());
        songResponse.setAlbumName(song.getAlbum().getTitle());
        return songResponse;
    }

    @Override
    public Song toEntity(SongRequest request) {
       if(request==null) return null;
       return Song.builder()
               .title(request.getTitle())
               .id(request.getArtistId())
               .duration(request.getDuration())
               .build();

    }

}
