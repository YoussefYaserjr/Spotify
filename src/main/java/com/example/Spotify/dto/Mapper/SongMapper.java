package com.example.Spotify.dto.Mapper;

import com.example.Spotify.dto.Request.SongRequest;
import com.example.Spotify.dto.Response.SongResponse;
import com.example.Spotify.entity.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {

    SongResponse toResponse(Song song);

    Song toEntity(SongRequest request);
}