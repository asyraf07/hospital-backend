package com.asyraf.hospital.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.asyraf.hospital.dto.DokterRequest;
import com.asyraf.hospital.dto.DokterResponse;
import com.asyraf.hospital.service.DokterService;

class DokterControllerTest {

    @InjectMocks
    private DokterController dokterController;

    @Mock
    private DokterService dokterService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    public DokterResponse responseGenerator(String nama, String noHp, String spesialisasi) {
        return DokterResponse.builder()
                .nama(nama)
                .noHp(noHp)
                .spesialisasi(spesialisasi)
                .userId(1)
                .build();
    }

    public DokterRequest requestGenerator(Integer userId, String spesialisasi) {
        return DokterRequest.builder()
                .userId(userId)
                .spesialisasi(spesialisasi)
                .build();
    }

    // @Test
    // public void testGetAllDokter() {
    //     List<DokterResponse> expectedList = new ArrayList<>();
    //     expectedList.add(responseGenerator("Asyraf", "08111223334", "Paru-paru"));
    //     expectedList.add(responseGenerator("Tono", "08123456789", "Mata"));
    //     when(dokterService.getAllDokter()).thenReturn(expectedList);

    //     ResponseEntity<List<DokterResponse>> actualList = dokterController.getAll();

    //     assertEquals(HttpStatus.OK, actualList.getStatusCode());
    //     assertEquals(expectedList.size(), actualList.getBody().size());
    // }

    // @Test
    // public void testGetAllDokterEmptyValue() {
    //     List<DokterResponse> expectedList = new ArrayList<>();
    //     when(dokterService.getAllDokter()).thenReturn(expectedList);

    //     ResponseEntity<List<DokterResponse>> actualList = dokterController.getAll();

    //     assertEquals(HttpStatus.OK, actualList.getStatusCode());
    //     assertTrue(expectedList.isEmpty());
    // }

    @Test
    void testGetOneDokterWithValidId() {
        Integer id = 1;
        DokterResponse expected = responseGenerator("Asyraf", "08111223334", "Paru-paru");
        when(dokterService.getDokter(id)).thenReturn(expected);

        ResponseEntity<DokterResponse> actual = dokterController.getById(id);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected.getNama(), actual.getBody().getNama());
        assertEquals(expected.getNoHp(), actual.getBody().getNoHp());
        assertEquals(expected.getSpesialisasi(), actual.getBody().getSpesialisasi());
        assertEquals(expected.getUserId(), actual.getBody().getUserId());
    }

    @Test
    void testGetOneDokterWithInvalidId() {
        Integer id = 1;
        ServiceException expected = new ServiceException("Tidak ada dokter dengan ID ini!");
        when(dokterService.getDokter(id)).thenThrow(expected);

        assertThrows(expected.getClass(), () -> dokterController.getById(id));
    }

    @Test
    void testPostDokterWithValidUserId() {
        DokterRequest request = requestGenerator(1, "Paru-paru");

        DokterResponse expected = responseGenerator("Asyraf", "08111223334", request.getSpesialisasi());
        when(dokterService.addDokter(request)).thenReturn(expected);

        ResponseEntity<DokterResponse> actual = dokterController.addDokter(request);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected.getNama(), actual.getBody().getNama());
        assertEquals(expected.getNoHp(), actual.getBody().getNoHp());
        assertEquals(expected.getSpesialisasi(), actual.getBody().getSpesialisasi());
        assertEquals(expected.getUserId(), actual.getBody().getUserId());
    }

    @Test
    void testPostDokterWithInvalidUserId() {
        DokterRequest request = requestGenerator(1, "Paru-paru");

        ServiceException expected = new ServiceException("Tidak ada user dengan ID ini!");
        when(dokterService.addDokter(request)).thenThrow(expected);

        assertThrows(expected.getClass(), () -> dokterController.addDokter(request));
    }

    @Test
    void testPutDokterWithValidUserIdAndDokterId() {
        Integer id = 1;
        DokterRequest request = requestGenerator(1, "Paru-paru");

        DokterResponse expected = responseGenerator("Asyraf", "08111223334", request.getSpesialisasi());
        when(dokterService.updateDokter(id, request)).thenReturn(expected);

        ResponseEntity<DokterResponse> actual = dokterController.updateDokter(id, request);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected.getNama(), actual.getBody().getNama());
        assertEquals(expected.getNoHp(), actual.getBody().getNoHp());
        assertEquals(expected.getSpesialisasi(), actual.getBody().getSpesialisasi());
        assertEquals(expected.getUserId(), actual.getBody().getUserId());
    }

    @Test
    void testPutDokterWithInvalidUserId() {
        Integer id = 1;
        DokterRequest request = requestGenerator(1, "Paru-paru");

        ServiceException expected = new ServiceException("Tidak ada user dengan ID ini!");
        when(dokterService.updateDokter(id, request)).thenThrow(expected);

        assertThrows(expected.getClass(), () -> dokterController.updateDokter(id, request));
    }

    @Test
    void testPutDokterWithInvalidDokterId() {
        Integer id = 1;
        DokterRequest request = requestGenerator(2, "Paru-paru");

        ServiceException expected = new ServiceException("Tidak ada dokter dengan ID ini!");
        when(dokterService.updateDokter(id, request)).thenThrow(expected);

        assertThrows(expected.getClass(), () -> dokterController.updateDokter(id, request));
    }

    @Test
    void testDeleteDokterWithValidId() {
        Integer id = 1;

        String expected = String.format("Tidak ada dokter dengan id %s", id);
        when(dokterService.deleteDokter(id)).thenReturn(expected);

        ResponseEntity<String> actual = dokterController.deleteDokter(id);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
    }

    @Test
    void testDeleteDokterWithInvalidId() {
        Integer id = 1;
        ServiceException expected = new ServiceException("Tidak ada dokter dengan ID ini!");
        when(dokterService.deleteDokter(id)).thenThrow(expected);

        assertThrows(expected.getClass(), () -> dokterController.deleteDokter(id));
    }

}
