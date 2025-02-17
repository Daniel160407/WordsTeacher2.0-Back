package com.wordsteacher2.service;

import com.wordsteacher2.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Integer logIn(UserDto userDto);

    void register(UserDto userDto);
}
