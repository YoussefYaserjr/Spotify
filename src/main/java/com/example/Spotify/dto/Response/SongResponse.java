package com.example.Spotify.dto.Response;


import lombok.Data;
@Data
public class SongResponse {

    private Long id;
    private String title;
    private String artistName;
    private String albumName;
    private Integer duration;
}