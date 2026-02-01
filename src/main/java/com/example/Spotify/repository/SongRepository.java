package com.example.Spotify.repository;

import com.example.Spotify.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    /**
     * Find song by ID with artist and album details
     */
    @Query("""
        select s
        from Song s
        left join fetch s.artist
        left join fetch s.album
        where s.id = :songId
    """)
    Optional<Song> findByIdWithDetails(@Param("songId") Long songId);

    /**
     * Find songs by title (case-insensitive partial match)
     */
    List<Song> findByTitleContainingIgnoreCase(String title);

    /**
     * Find all songs by artist
     */
    List<Song> findByArtistId(Long artistId);

    /**
     * Find all songs by album
     */
    List<Song> findByAlbumId(Long albumId);

    /**
     * Find all songs by artist with full details
     */
    @Query("""
        select s
        from Song s
        join fetch s.artist a
        left join fetch s.album
        where a.id = :artistId
        order by s.title
    """)
    List<Song> findByArtistIdWithDetails(@Param("artistId") Long artistId);

    /**
     * Find all songs by album with full details
     */
    @Query("""
        select s
        from Song s
        left join fetch s.artist
        join fetch s.album alb
        where alb.id = :albumId
        order by s.title
    """)
    List<Song> findByAlbumIdWithDetails(@Param("albumId") Long albumId);

    /**
     * Search songs by title, artist name, or album name
     */
    @Query("""
        select distinct s
        from Song s
        left join fetch s.artist a
        left join fetch s.album alb
        where lower(s.title) like lower(concat('%', :searchTerm, '%'))
        or lower(a.name) like lower(concat('%', :searchTerm, '%'))
        or lower(alb.title) like lower(concat('%', :searchTerm, '%'))
        order by s.title
    """)
    List<Song> searchSongs(@Param("searchTerm") String searchTerm);
}