package com.asyraf.hospital.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DokterRequest {

    private Integer userId;
    private String spesialisasi;

}
