package com.asyraf.hospital.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PengobatanResponse {

    private Integer id;
    private String namaPasien;
    private String namaDokter;
    private String penyakit;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date tanggalPengobatan;

}
