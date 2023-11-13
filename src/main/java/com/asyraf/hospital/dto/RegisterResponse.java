package com.asyraf.hospital.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class RegisterResponse {

    private Integer id;
    private String nama;
    private String noHp;

}
