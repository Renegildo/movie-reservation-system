package com.renegildo.movie_reservation_system.role;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
}
