package com.google.cms.dtos.auth_dto;

import com.google.cms.dtos.res.RoleResDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class JwtResponsedto {
    private boolean otpEnabled = false;
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private RoleResDTO role;
    private String Status;
}
