package com.example.Spotify.dto.Mapper;

import com.example.Spotify.dto.Request.AlbumRequest;
import com.example.Spotify.dto.Response.AlbumResponse;
import com.example.Spotify.entity.Album;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ArtistMapper.class})
public interface AlbumMapper {

    AlbumResponse toResponse(Album album);

    Album toEntity(AlbumRequest request);
}
