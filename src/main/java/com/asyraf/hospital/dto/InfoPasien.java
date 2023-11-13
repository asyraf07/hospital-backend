package com.asyraf.hospital.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoPasien {

    private String namaPasien;
    private String namaDokter;
    private String penyakit;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Data tanggalMasuk;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Data tanggalKeluar;

    
}
