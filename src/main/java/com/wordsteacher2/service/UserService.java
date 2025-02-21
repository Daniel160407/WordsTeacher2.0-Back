package com.wordsteacher2.service;

import com.wordsteacher2.dto.UserDto;
import com.wordsteacher2.freemius.model.PlanWithLanguageId;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    PlanWithLanguageId logIn(UserDto userDto);

    Integer register(UserDto userDto);
}
