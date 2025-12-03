package com.example.Spotify.dto.Mapper;

import com.example.Spotify.dto.Request.PlaylistRequest;
import com.example.Spotify.dto.Response.PlaylistResponse;
import com.example.Spotify.entity.Playlist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, PlaylistSongMapper.class})
public interface PlaylistMapper {

    PlaylistResponse toResponse(Playlist playlist);

    Playlist toEntity(PlaylistRequest request);
}
