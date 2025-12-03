package com.example.Spotify.dto.Mapper.imp;

import com.example.Spotify.dto.Mapper.PlaylistSongMapper;
import com.example.Spotify.dto.Request.PlaylistSongRequest;
import com.example.Spotify.dto.Response.PlaylistSongResponse;
import com.example.Spotify.entity.PlaylistSong;
import org.springframework.stereotype.Component;

@Component
public class PlaylistSongMapperImpl implements PlaylistSongMapper {
    @Override
    public PlaylistSongResponse toResponse(PlaylistSong ps) {
        if (ps == null) return null;
        PlaylistSongResponse response = new PlaylistSongResponse();
        response.setSongId(ps.getSong().getId());
        response.setTitle(ps.getSong().getTitle());
        response.setOrderIndex(ps.getOrderIndex());
        return response;
    }

    @Override
    public PlaylistSong toEntity(PlaylistSongRequest request) {
        if (request == null) return null;

        PlaylistSong ps = new PlaylistSong();
        ps.setOrderIndex(request.getOrderIndex());
        return ps;
    }
}
