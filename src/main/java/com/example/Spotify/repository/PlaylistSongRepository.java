package com.example.Spotify.repository;

import com.example.Spotify.entity.PlaylistSong;
import com.example.Spotify.entity.PlaylistSongId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, PlaylistSongId> {

    /**
     * Find all songs in a playlist ordered by position
     */
    List<PlaylistSong> findByPlaylistIdOrderByOrderIndexAsc(Long playlistId);

    /**
     * Find all playlists containing a specific song
     */
    List<PlaylistSong> findBySongId(Long songId);

    /**
     * Count total songs in a playlist
     */
    long countByPlaylistId(Long playlistId);

    /**
     * Check if a specific song exists in a playlist
     */
    boolean existsByPlaylistIdAndSongId(Long playlistId, Long songId);

    /**
     * Delete all songs from a playlist
     */
    void deleteByPlaylistId(Long playlistId);

    /**
     * Get the maximum order index in a playlist
     */
    @Query("select max(ps.orderIndex) from PlaylistSong ps where ps.playlist.id = :playlistId")
    Optional<Integer> findMaxOrderIndexByPlaylistId(@Param("playlistId") Long playlistId);

    /**
     * Find playlist song with full details (song, artist, album)
     */
    @Query("""
        select ps
        from PlaylistSong ps
        join fetch ps.song s
        left join fetch s.artist
        left join fetch s.album
        where ps.playlist.id = :playlistId
        and ps.song.id = :songId
    """)
    Optional<PlaylistSong> findByPlaylistIdAndSongIdWithDetails(
            @Param("playlistId") Long playlistId,
            @Param("songId") Long songId);

    /**
     * Find all songs in a playlist with full details
     */
    @Query("""
        select ps
        from PlaylistSong ps
        join fetch ps.song s
        left join fetch s.artist
        left join fetch s.album
        where ps.playlist.id = :playlistId
        order by ps.orderIndex asc
    """)
    List<PlaylistSong> findByPlaylistIdWithDetails(@Param("playlistId") Long playlistId);
}