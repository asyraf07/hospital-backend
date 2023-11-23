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

import com.asyraf.hospital.dto.PasienRequest;
import com.asyraf.hospital.dto.PasienResponse;
import com.asyraf.hospital.service.PasienService;

class PasienControllerTest {

    @InjectMocks
    private PasienController pasienController;

    @Mock
    private PasienService pasienService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    public PasienResponse responseGenerator(String nama, String noHp) {
        return PasienResponse.builder()
                .nama(nama)
                .noHp(noHp)
                .userId(1)
                .build();
    }

    public PasienRequest requestGenerator(Integer userId) {
        return PasienRequest.builder()
                .userId(userId)
                .build();
    }

    @Test
    void testGetAllPasien() {
        List<PasienResponse> expectedList = new ArrayList<>();
        expectedList.add(responseGenerator("Asyraf", "08111223334"));
        expectedList.add(responseGenerator("Tono", "08123456789"));
        when(pasienService.getAllPasien()).thenReturn(expectedList);

        ResponseEntity<List<PasienResponse>> actualList = pasienController.getAll();

        assertEquals(HttpStatus.OK, actualList.getStatusCode());
        assertEquals(expectedList.size(), actualList.getBody().size());
    }

    @Test
    void testGetAllPasienEmptyValue() {
        List<PasienResponse> expectedList = new ArrayList<>();
        when(pasienService.getAllPasien()).thenReturn(expectedList);

        ResponseEntity<List<PasienResponse>> actualList = pasienController.getAll();

        assertEquals(HttpStatus.OK, actualList.getStatusCode());
        assertTrue(expectedList.isEmpty());
    }

    @Test
    void testGetOnePasienWithValidId() {
        Integer id = 1;
        PasienResponse expected = responseGenerator("Asyraf", "08111223334");
        when(pasienService.getPasien(id)).thenReturn(expected);

        ResponseEntity<PasienResponse> actual = pasienController.getById(id);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals("Asyraf", actual.getBody().getNama());
        assertEquals("08111223334", actual.getBody().getNoHp());
        assertEquals(1, actual.getBody().getUserId());
    }

    @Test
    void testGetOnePasienWithInvalidId() {
        Integer id = 1;
        ServiceException expectedException = new ServiceException("Tidak ada pasien dengan ID ini!");
        when(pasienService.getPasien(id)).thenThrow(expectedException);

        assertThrows(expectedException.getClass(), () -> pasienController.getById(id));
    }

    @Test
    void testPostPasienWithValidUserId() {
        PasienRequest request = requestGenerator(1);

        PasienResponse expected = responseGenerator("Asyraf", "08111223334");
        when(pasienService.addPasien(request)).thenReturn(expected);

        ResponseEntity<PasienResponse> actual = pasienController.addPasien(request);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals("Asyraf", actual.getBody().getNama());
        assertEquals("08111223334", actual.getBody().getNoHp());
        assertEquals(1, actual.getBody().getUserId());
    }

    @Test
    void testPostPasienWithInvalidUserId() {
        PasienRequest request = requestGenerator(1);

        ServiceException expected = new ServiceException("Tidak ada user dengan ID ini!");
        when(pasienService.addPasien(request)).thenThrow(expected);

        assertThrows(expected.getClass(), () -> pasienController.addPasien(request));
    }

    @Test
    void testPutPasienWithValidUserIdAndPasienId() {
        Integer id = 1;
        PasienRequest request = requestGenerator(1);

        PasienResponse expected = responseGenerator("Asyraf", "08111223334");
        when(pasienService.updatePasien(id, request)).thenReturn(expected);

        ResponseEntity<PasienResponse> actual = pasienController.updatePasien(id, request);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals("Asyraf", actual.getBody().getNama());
        assertEquals("08111223334", actual.getBody().getNoHp());
        assertEquals(1, actual.getBody().getUserId());
    }

    @Test
    void testPutPasienWithInvalidUserId() {
        Integer id = 1;
        PasienRequest request = requestGenerator(1);

        ServiceException expected = new ServiceException("Tidak ada user dengan ID ini!");
        when(pasienService.updatePasien(id, request)).thenThrow(expected);

        assertThrows(expected.getClass(), () -> pasienController.updatePasien(id, request));
    }

    @Test
    void testPutPasienWithInvalidPasienId() {
        Integer id = 1;
        PasienRequest request = requestGenerator(2);

        ServiceException expected = new ServiceException("Tidak ada pasien dengan ID ini!");
        when(pasienService.updatePasien(id, request)).thenThrow(expected);

        assertThrows(expected.getClass(), () -> pasienController.updatePasien(id, request));
    }

    @Test
    void testDeletePasienWithValidId() {
        Integer id = 1;

        String expected = String.format("Tidak ada pasien dengan id %s", id);
        when(pasienService.deletePasien(id)).thenReturn(expected);

        ResponseEntity<String> actual = pasienController.deletePasien(id);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
    }

    @Test
    void testDeletePasienWithInvalidId() {
        Integer id = 1;
        ServiceException expected = new ServiceException("Tidak ada pasien dengan ID ini!");
        when(pasienService.deletePasien(id)).thenThrow(expected);

        assertThrows(expected.getClass(), () -> pasienController.deletePasien(id));
    }

}
