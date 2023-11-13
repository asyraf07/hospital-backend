package com.asyraf.hospital.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class DokterResponse {

    private Integer id;
    private String nama;
    private String noHp;
    private String spesialisasi;
    private Integer userId;

}
