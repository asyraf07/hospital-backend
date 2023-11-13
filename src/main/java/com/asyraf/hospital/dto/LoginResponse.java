package com.asyraf.hospital.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class LoginResponse {

    private String token;

}
