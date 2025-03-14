package com.wordsteacher2.controller;

import com.wordsteacher2.dto.UserDto;
import com.wordsteacher2.freemius.model.PlanWithLanguageId;
import com.wordsteacher2.freemius.service.exception.NoPermissionException;
import com.wordsteacher2.service.UserService;
import com.wordsteacher2.service.exception.InvalidEmailOrPasswordException;
import com.wordsteacher2.service.exception.UserAlreadyRegisteredException;
import com.wordsteacher2.util.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", exposedHeaders = "Authorization")
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PutMapping
    public ResponseEntity<?> login(@RequestBody UserDto userDto, HttpServletResponse response) {
        try {
            PlanWithLanguageId plan = userService.logIn(userDto);
            val token = jwtUtils.generateToken(userDto.getEmail());
            response.addHeader(jwtUtils.JWT_HEADER, jwtUtils.JWT_PREFIX + token);
            return ResponseEntity.accepted().body(plan);
        } catch (InvalidEmailOrPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userDto));
        } catch (UserAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@RequestParam Integer userid) {
        try {
            userService.deleteAccount(userid);
            return ResponseEntity.ok().build();
        } catch (NoPermissionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
