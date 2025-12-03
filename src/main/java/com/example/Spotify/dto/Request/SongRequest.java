package com.example.Spotify.dto.Request;

import lombok.Data;

@Data
public class SongRequest {


    private String title;
    private Long artistId;
    private Integer duration;


}