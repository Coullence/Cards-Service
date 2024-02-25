package com.google.cms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrivilegeDTO {
    private String id;
    private String name;
    private boolean selected;
    private int code;
}
