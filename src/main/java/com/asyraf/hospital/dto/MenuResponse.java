package com.asyraf.hospital.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class MenuResponse {

    private Integer id;
    private String namaLayanan;
    private String urlImage;

}
