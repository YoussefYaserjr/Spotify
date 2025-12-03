package com.example.Spotify.dto.Mapper;

import com.example.Spotify.dto.Request.PlaylistSongRequest;
import com.example.Spotify.dto.Response.PlaylistSongResponse;
import com.example.Spotify.entity.PlaylistSong;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SongMapper.class, PlaylistMapper.class})
public interface PlaylistSongMapper {

    @Mapping(source = "playlist.id", target = "songId")
    @Mapping(source = "song.id", target = "songId")
    PlaylistSongResponse toResponse(PlaylistSong playlistSong);

    @Mapping(source = "songId", target = "playlist.id")
    @Mapping(source = "songId", target = "song.id")
    PlaylistSong toEntity(PlaylistSongRequest request);
}
