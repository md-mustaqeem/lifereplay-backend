package com.life.ai.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String mobile;
    private String email;
    private String password;
}
