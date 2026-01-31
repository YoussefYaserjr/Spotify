package com.example.Spotify.repository;

import com.example.Spotify.dto.Response.AlbumResponse;
import com.example.Spotify.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByArtistId(Long artistId);
    @Query("""
    select a
    from Album a
    join fetch a.artist
    where upper(a.title) like upper(concat('%', :title, '%'))
""")
    List<Album> searchAlbums(@Param("title")String title);

}
