package com.renegildo.movie_reservation_system.config;

import com.renegildo.movie_reservation_system.common.RoleConstants;
import com.renegildo.movie_reservation_system.genre.Genre;
import com.renegildo.movie_reservation_system.genre.GenreRepository;
import com.renegildo.movie_reservation_system.role.Role;
import com.renegildo.movie_reservation_system.role.RoleRepository;
import com.renegildo.movie_reservation_system.user.User;
import com.renegildo.movie_reservation_system.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final PasswordEncoder passwordEncoder;
    private final List<String> initialRoles = List.of(RoleConstants.USER, RoleConstants.ADMIN);

    public DataLoader(
            RoleRepository roleRepository,
            UserRepository userRepository,
            GenreRepository genreRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createDefaultRoles();
        createDefaultAdminUser();
        createDefaultGenres();
    }

    private void createDefaultRoles() {
        initialRoles.forEach(roleName -> {
            Optional<Role> existingRole = roleRepository.findByName(roleName);
            if (existingRole.isPresent()) return;

            Role newRole = new Role();
            newRole.setName(roleName);
            roleRepository.save(newRole);
        });
    }

    private void createDefaultAdminUser() {
        String adminEmail = "admin@email.com";
        String adminUsername = "admin";
        String adminPassword = "abc123";
        String adminRoleName = RoleConstants.ADMIN;

        if (userRepository.findByEmail(adminEmail).isPresent()) return;

        Role adminRole = roleRepository.findByName(adminRoleName).orElseThrow();
        User adminUser = new User();
        String encodedPassword = passwordEncoder.encode(adminPassword);
        adminUser.setEmail(adminEmail);
        adminUser.setUsername(adminUsername);
        adminUser.setPassword(encodedPassword);
        adminUser.setRole(adminRole);
        userRepository.save(adminUser);
    }

    private void createDefaultGenres() {
        List<String> defaultGenreNames = List.of("horror", "comedy", "action", "fiction");

        defaultGenreNames.forEach(genreName -> {
            Genre newGenre = new Genre();
            newGenre.setName(genreName);

            genreRepository.save(newGenre);
        });
    }
}
