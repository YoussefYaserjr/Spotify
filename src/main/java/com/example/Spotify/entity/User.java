package  com.example.Spotify.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password; // bcrypt
    private String role; // ROLE_USER or ROLE_ARTIST
    private String username;
    private String country;
    @OneToMany(mappedBy = "user")
    private List<Playlist>playlists;
}