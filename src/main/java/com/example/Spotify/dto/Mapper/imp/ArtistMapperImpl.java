package com.example.Spotify.dto.Mapper.imp;


import com.example.Spotify.dto.Mapper.ArtistMapper;
import com.example.Spotify.dto.Request.ArtistRequest;
import com.example.Spotify.dto.Response.ArtistResponse;
import com.example.Spotify.entity.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapperImpl implements ArtistMapper {


    @Override
    public ArtistResponse toResponse(Artist artist) {
    if(artist==null) return null;

     /*  return ArtistResponse.builder()
                .id(artist.getId())
                .name(artist.getName())
                .country(artist.getCountry())
                .build();*/
        ArtistResponse response = new ArtistResponse();
        response.setId(artist.getId());
        response.setName(artist.getName());
        response.setCountry(artist.getCountry());
        return response;
    }
    @Override
    public Artist toEntity(ArtistRequest request) {
        if (request==null) return null;
        return Artist.builder()
                .name(request.getName())
                .build();
    }
}