package com.asyraf.hospital.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

@Data
public class RegisterRequest {

    // @NotBlank(message = "Please enter a name")
    private String nama;
    // @NotBlank(message = "Please enter a nomor hp")
    private String noHp;
    private String spesialis;

}
