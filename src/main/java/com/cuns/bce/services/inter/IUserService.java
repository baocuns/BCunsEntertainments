package com.cuns.bce.services.inter;

import com.cuns.bce.dto.auth.UserDto;
import com.cuns.bce.dto.request.auth.UserRegisterDto;
import com.cuns.bce.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService {
    Optional<User> findByUsername(String username);
    User registerNewUserAccount(UserRegisterDto userRegisterDto) throws Exception;
}
