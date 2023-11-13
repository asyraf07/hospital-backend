package com.asyraf.hospital.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class KamarResponse {

    private Integer id;
    private String noKamar;

}
