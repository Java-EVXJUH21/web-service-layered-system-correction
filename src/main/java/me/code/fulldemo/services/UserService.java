package me.code.fulldemo.services;


import lombok.extern.slf4j.Slf4j;
import me.code.fulldemo.exceptions.ProductAlreadyExistsException;
import me.code.fulldemo.exceptions.UserAlreadyExistsException;
import me.code.fulldemo.models.Product;
import me.code.fulldemo.models.User;
import me.code.fulldemo.repositories.UserRepository;
import me.code.fulldemo.security.UserObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("A user with username '" + username + "' could not be found."));

        return new UserObject(user);
    }
}
