package com.example.Spotify.repository;

import com.example.Spotify.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    @Query("""
    select distinct p
    from Playlist p
    left join fetch p.user u
    left join fetch p.playlistSongs ps
    left join fetch ps.song s
    left join fetch s.artist
    left join fetch s.album
    where p.id = :playlistId
    order by ps.orderIndex
""")
    Optional<Playlist> findByIdWithSongs(@Param("playlistId") Long playlistId);

    @Query("""
    select distinct p
    from Playlist p
    left join fetch p.user u
    left join fetch p.playlistSongs ps
    left join fetch ps.song s
    left join fetch s.artist
    left join fetch s.album
    order by ps.orderIndex
""")
    List<Playlist> findAllWithSongs();
    List<Playlist> findByUserId(Long userId);
}
