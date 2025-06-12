package com.wordsteacher2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String email;
    private String password;
    private String language;
    private String plan;
    private String registrationDate;

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", language='" + language + '\'' +
                ", plan='" + plan + '\'' +
                '}';
    }
}
