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
    private static final List<String> DEFAULT_GENRES = List.of("horror", "comedy", "action", "fiction");
    private static final List<String> DEFAULT_ROLES = List.of(RoleConstants.USER, RoleConstants.ADMIN);

    private static final String ADMIN_EMAIL = "admin@email.com";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "abc123";

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final PasswordEncoder passwordEncoder;

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

    private void createRoleIfNotExists(String roleName) {
        Optional<Role> existingRole = roleRepository.findByName(roleName);
        if (existingRole.isPresent()) return;

        Role newRole = new Role();
        newRole.setName(roleName);
        roleRepository.save(newRole);
    }

    private void createDefaultRoles() { DataLoader.DEFAULT_ROLES.forEach(this::createRoleIfNotExists); }

    private void createDefaultAdminUser() {
        if (userRepository.findByEmail(DataLoader.ADMIN_EMAIL).isPresent()) return;

        Role adminRole = roleRepository.findByName(RoleConstants.ADMIN)
                .orElseThrow(() -> new IllegalStateException("Admin role not found."));

        User adminUser = new User();
        String encodedPassword = passwordEncoder.encode(DataLoader.ADMIN_PASSWORD);

        adminUser.setEmail(DataLoader.ADMIN_EMAIL);
        adminUser.setUsername(DataLoader.ADMIN_USERNAME);
        adminUser.setPassword(encodedPassword);
        adminUser.setRole(adminRole);
        userRepository.save(adminUser);
    }

    private void createGenreIfNotExists(String genreName) {
        if (genreRepository.findByName(genreName).isPresent()) return;

        Genre newGenre = new Genre();
        newGenre.setName(genreName);
        genreRepository.save(newGenre);
    }

    private void createDefaultGenres() { DataLoader.DEFAULT_GENRES.forEach(this::createGenreIfNotExists); }
}
