package com.asyraf.hospital.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class KamarPasienRequest {

    private Integer pasienId;
    private Integer kamarId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date tanggalMasuk;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date tanggalKeluar;

}
