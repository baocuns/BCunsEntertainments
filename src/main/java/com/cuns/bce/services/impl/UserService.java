package com.cuns.bce.services.impl;

import com.cuns.bce.dto.auth.UserDto;
import com.cuns.bce.dto.request.auth.UserRegisterDto;
import com.cuns.bce.entities.Authority;
import com.cuns.bce.entities.Profile;
import com.cuns.bce.entities.User;
import com.cuns.bce.repositories.AuthorityRepository;
import com.cuns.bce.repositories.ProfileRepository;
import com.cuns.bce.repositories.UserRepository;
import com.cuns.bce.services.inter.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {
    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;
    public final AuthorityRepository authorityRepository;
    final private ProfileRepository profileRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        Set<Authority> authorities = authorityRepository.findAllByUser(user.get());
        // set authorities for user
        user.get().setAuthorities(authorities);

        return user;
    }

    @Override
    public void registerNewUserAccount(UserRegisterDto userRegisterDto) throws Exception {
        if (emailExists(userRegisterDto.getEmail())) {
            throw new Exception("There is an account with that email address: " + userRegisterDto.getEmail());
        }
        if (userRepository.findByUsername(userRegisterDto.getUsername()).isPresent()) {
            throw new Exception("There is an account with that username: " + userRegisterDto.getUsername());
        }

        try {
            // save user
            User user = new User(userRegisterDto.getUsername(), userRegisterDto.getEmail(), passwordEncoder.encode(userRegisterDto.getPassword()));
            User registered = userRepository.save(user);

            // create default authority
            Authority authority = new Authority(registered, "ROLE_USER");
            authorityRepository.save(authority);

            // create default user profile
            Profile profile = new Profile(registered.getUsername(), registered, registered.getUsername());
            profileRepository.save(profile);
        } catch (Exception e) {
            log.error("Error while registering new user account: " + e.getMessage());
            throw new Exception("Error while registering new user account: " + e.getMessage());
        }
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
