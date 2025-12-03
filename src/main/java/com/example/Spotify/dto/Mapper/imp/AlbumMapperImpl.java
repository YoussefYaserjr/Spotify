package com.example.Spotify.dto.Mapper.imp;


import com.example.Spotify.dto.Mapper.AlbumMapper;
import com.example.Spotify.dto.Request.AlbumRequest;
import com.example.Spotify.dto.Response.AlbumResponse;
import com.example.Spotify.entity.Album;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapperImpl implements AlbumMapper {


    @Override
    public AlbumResponse toResponse(Album album) {
        if(album==null)return null;
      /*  return AlbumResponse.builder()
                .id(album.getId())
                .title(album.getTitle())
                .artistName(album.getArtist().getName())
                .build();*/
        AlbumResponse response = new AlbumResponse();
        response.setId(album.getId());
        response.setTitle(album.getTitle());
        response.setArtistName(album.getArtist().getName());
        return response;
    }

    @Override
    public Album toEntity(AlbumRequest request) {
       if(request==null) return null;
       return Album.builder()
               .title(request.getTitle())
               .id(request.getArtistId())
               .build();
    }
}
