package com.renegildo.movie_reservation_system.user;

import com.renegildo.movie_reservation_system.common.RoleConstants;
import com.renegildo.movie_reservation_system.exception.EmailAlreadyUsedException;
import com.renegildo.movie_reservation_system.exception.NotFoundException;
import com.renegildo.movie_reservation_system.exception.UserAlreadyAdminException;
import com.renegildo.movie_reservation_system.exception.UsernameAlreadyUsedException;
import com.renegildo.movie_reservation_system.role.Role;
import com.renegildo.movie_reservation_system.role.RoleService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private Role newUserDefaultRole;

    public UserService(
            UserRepository userRepository,
            RoleService roleService,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDefaultRole() throws RuntimeException {
        String defaultUserRoleName = RoleConstants.USER;
        this.newUserDefaultRole = roleService.getByName(defaultUserRoleName);
    }

    public void registerUser(UserRegisterDTO body) {
        checkIfUserExists(body.getEmail(), body.getPassword());

        String hashedPassword = passwordEncoder.encode(body.getPassword());

        User newUser = new User();
        newUser.setEmail(body.getEmail());
        newUser.setUsername(body.getUsername());
        newUser.setPassword(hashedPassword);
        newUser.setRole(newUserDefaultRole);

        userRepository.save(newUser);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void promoteToAdmin(Long userId) throws RuntimeException {
        User user = this.getById(userId);

        boolean userAlreadyIsAdmin = user.getRole().getName().equals(RoleConstants.ADMIN);
        if (userAlreadyIsAdmin)
            throw new UserAlreadyAdminException("User is already an admin.");

        Role adminRole = roleService.getByName(RoleConstants.ADMIN);
        user.setRole(adminRole);
        userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found."));
    }

    private void checkIfUserExists(String email, String username) {
        Optional<User> existingEmailUser = userRepository.findByEmail(email);
        if (existingEmailUser.isPresent())
            throw new EmailAlreadyUsedException("Email already in use.");

        Optional<User> existingUsernameUser = userRepository.findByUsername(username);
        if (existingUsernameUser.isPresent())
            throw new UsernameAlreadyUsedException("Username already in use.");
    }
}
