package com.asyraf.hospital.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PengobatanRequest {

    private Integer pasienId;
    private Integer dokterId;
    private String penyakit;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date tanggalPengobatan;

}
