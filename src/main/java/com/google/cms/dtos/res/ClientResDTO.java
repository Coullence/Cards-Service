package com.google.cms.dtos.res;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientResDTO {
    private String name;
    private List<String> privileges;
}
