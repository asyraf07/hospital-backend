package com.asyraf.hospital.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class LoginResponse {

    private Integer userId;
    private String token;
    private Integer pasienId;

}
