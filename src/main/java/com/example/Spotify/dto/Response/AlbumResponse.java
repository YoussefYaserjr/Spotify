package com.example.Spotify.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
public class AlbumResponse
{
    private Long id;
    private String title;
    private String coverImage;
    private String artistName;
}
