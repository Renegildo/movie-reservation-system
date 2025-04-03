package com.renegildo.movie_reservation_system.user;

import com.renegildo.movie_reservation_system.role.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "app_user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Role role;
}
