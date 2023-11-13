package com.asyraf.hospital.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DetailKamar {

    private String nomorKamar;
    private List<InfoPasien> listPasien;
    
}
