package me.code.fulldemo.controllers;

import lombok.extern.slf4j.Slf4j;
import me.code.fulldemo.dtos.UserCreation;
import me.code.fulldemo.dtos.UserPayload;
import me.code.fulldemo.exceptions.UserAlreadyExistsException;
import me.code.fulldemo.models.User;
import me.code.fulldemo.repositories.UserRepository;
import me.code.fulldemo.security.UserObject;
import me.code.fulldemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PutMapping("/register")
    public UserPayload registerUser(@RequestBody UserCreation creation)
            throws UserAlreadyExistsException
    {
        var existing = userRepository.findByUsername(creation.getUsername());
        if (existing.isPresent()) {
            log.info("Failed to register user since name '" + creation.getUsername() + "' already exists.");
            throw new UserAlreadyExistsException();
        }

        var user = new User(creation.getUsername(), passwordEncoder.encode(creation.getPassword()), creation.isAdmin());
        log.info("Successfully registered user with id '" + user.getId() + "'.");
        return UserPayload.fromUser(userRepository.save(user));
    }

    @GetMapping("/info")
    public UserPayload info(@AuthenticationPrincipal UserObject user) {
        return UserPayload.fromUser(user.getUser());
    }

}
