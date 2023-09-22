package com.cuns.bce.services.impl;

import com.cuns.bce.dto.auth.UserDto;
import com.cuns.bce.dto.request.auth.UserRegisterDto;
import com.cuns.bce.entities.Authority;
import com.cuns.bce.entities.User;
import com.cuns.bce.repositories.AuthorityRepository;
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
    public User registerNewUserAccount(UserRegisterDto userRegisterDto) throws Exception {
        if (emailExists(userRegisterDto.getEmail())) {
            throw new Exception("There is an account with that email address: " + userRegisterDto.getEmail());
        }

        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setEnabled(true);
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        // save user
        User registered = userRepository.save(user);

        // create default authority
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUser(registered);
        authorityRepository.save(authority);

        return null;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
