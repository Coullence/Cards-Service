package com.google.cms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PasswordResetRequest {
    private String emailAddress;
    private String password;
    private String confirmPassword;
}
