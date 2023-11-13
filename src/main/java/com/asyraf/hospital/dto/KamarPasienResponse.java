package com.asyraf.hospital.dto;

import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Data
@Builder
public class KamarPasienResponse {

    private Integer id;
    private String namaPasien;
    private String noKamar;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date tanggalMasuk;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date tanggalKeluar;

}
