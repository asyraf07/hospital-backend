package com.asyraf.hospital.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.asyraf.hospital.dto.DokterRequest;
import com.asyraf.hospital.dto.DokterResponse;
import com.asyraf.hospital.entity.Dokter;
import com.asyraf.hospital.entity.User;
import com.asyraf.hospital.repository.DokterRepository;
import com.asyraf.hospital.repository.UserRepository;

class DokterServiceTest {

    @InjectMocks
    private DokterService dokterService;

    @Mock
    private DokterRepository dokterRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    public Dokter dokterGenerator(Integer id, String spesialisasi, User user) {
        Dokter dokter = new Dokter();
        dokter.setId(id);
        dokter.setSpesialisasi(spesialisasi);
        dokter.setUser(user);
        return dokter;
    }

    public User userGenerator(Integer id, String nama, String noHp) {
        User user = new User();
        user.setId(id);
        user.setNama(nama);
        user.setNoHP(noHp);
        return user;
    }

    public DokterRequest requestGenerator(Integer userId, String spesialisasi) {
        return DokterRequest.builder()
                .userId(userId)
                .spesialisasi(spesialisasi)
                .build();
    }

    @Test
    void testGetAllDokter() {
        User user1 = userGenerator(1, "Asyraf", "08111226664");
        Dokter dokter1 = dokterGenerator(1, "Paru-paru", user1);

        User user2 = userGenerator(2, "Budi", "08111223334");
        Dokter dokter2 = dokterGenerator(2, "Mata", user2);

        List<Dokter> expectedList = new ArrayList<>();
        expectedList.add(dokter1);
        expectedList.add(dokter2);

        when(dokterRepository.findAll()).thenReturn(expectedList);

        List<DokterResponse> actual = dokterService.getAllDokter();

        assertEquals(expectedList.size(), actual.size());
    }

    @Test
    void testGetOneDokterWithValidId() {
        Integer id = 1;
        User user = userGenerator(1, "Asyraf", "08111226664");
        Dokter expected = dokterGenerator(1, "Paru-paru", user);
        when(dokterRepository.findById(id)).thenReturn(Optional.of(expected));

        DokterResponse actual = dokterService.getDokter(id);

        assertEquals(expected.getUser().getNama(), actual.getNama());
        assertEquals(expected.getSpesialisasi(), actual.getSpesialisasi());
    }

    @Test
    void testGetOneDokterWithInvalidId() {
        Integer id = 1;
        ServiceException expectedException = new ServiceException("Tidak ada dokter dengan ID ini!");
        when(dokterRepository.findById(id)).thenThrow(expectedException);

        assertThrows(expectedException.getClass(), () -> dokterService.getDokter(id));
    }

    @Test
    void testPostDokterWithValidUserId() {
        Integer id = 1;
        User user = userGenerator(1, "Asyraf", "08111226664");
        Dokter dokter = dokterGenerator(id, "Paru-paru", user);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(dokterRepository.save(dokter)).thenReturn(dokter);

        DokterRequest request = requestGenerator(user.getId(), dokter.getSpesialisasi());
        DokterResponse actual = dokterService.addDokter(request);

        assertEquals(dokter.getUser().getNama(), actual.getNama());
        assertEquals(dokter.getUser().getNoHP(), actual.getNoHp());
        assertEquals(dokter.getSpesialisasi(), actual.getSpesialisasi());
        assertEquals(dokter.getUser().getId(), actual.getUserId());
    }

    @Test
    void testPostDokterWithInvalidUserId() {
        Integer id = 1;
        User user = userGenerator(1, "Asyraf", "08111226664");
        Dokter dokter = dokterGenerator(1, "Paru-paru", user);

        ServiceException expected = new ServiceException("Tidak ada user dengan ID ini!");
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        DokterRequest request = requestGenerator(user.getId(), dokter.getSpesialisasi());

        assertThrows(expected.getClass(), () -> dokterService.addDokter(request));
    }

    @Test
    void testPutDokterWithValidUserIdAndDokterId() {
        Integer id = 1;
        User user1 = userGenerator(1, "Asyraf", "08111226664");
        User user2 = userGenerator(2, "Budi", "08111223334");

        Dokter dokter = dokterGenerator(id, "Paru-paru", user1);

        Dokter updated = dokterGenerator(id, "Mata", user2);

        when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));
        when(dokterRepository.findById(id)).thenReturn(Optional.of(dokter));
        when(dokterRepository.save(dokter)).thenReturn(updated);

        DokterRequest request = requestGenerator(user2.getId(), updated.getSpesialisasi());
        DokterResponse actual = dokterService.updateDokter(id, request);

        assertEquals(dokter.getUser().getNama(), actual.getNama());
        assertEquals(dokter.getUser().getNoHP(), actual.getNoHp());
        assertEquals(dokter.getSpesialisasi(), actual.getSpesialisasi());
        assertEquals(dokter.getUser().getId(), actual.getUserId());
    }

    @Test
    void testPutDokterWithInvalidUserId() {
        Integer id = 1;
        User user = userGenerator(1, "Asyraf", "08111226664");
        Dokter dokter = dokterGenerator(1, "Paru-paru", user);

        ServiceException expected = new ServiceException("Tidak ada user dengan ID ini!");
        when(dokterRepository.findById(id)).thenReturn(Optional.of(dokter));
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        DokterRequest request = requestGenerator(user.getId(), dokter.getSpesialisasi());

        assertThrows(expected.getClass(), () -> dokterService.addDokter(request));
    }

    @Test
    void testPutDokterWithInvalidDokterId() {
        Integer id = 1;
        User user = userGenerator(1, "Asyraf", "08111226664");
        Dokter dokter = dokterGenerator(1, "Paru-paru", user);

        ServiceException expected = new ServiceException("Tidak ada user dengan ID ini!");
        when(dokterRepository.findById(id)).thenReturn(Optional.empty());

        DokterRequest request = requestGenerator(user.getId(), dokter.getSpesialisasi());

        assertThrows(expected.getClass(), () -> dokterService.addDokter(request));
    }

    @Test
    void testDeleteDokterWithValidId() {
        Integer id = 1;
        User user = userGenerator(1, "Asyraf", "08111226664");
        Dokter dokter = dokterGenerator(1, "Paru-paru", user);

        String expected = String.format("Dokter dengan id %s berhasil dihapus!", id);
        when(dokterRepository.findById(id)).thenReturn(Optional.of(dokter));

        String actual = dokterService.deleteDokter(id);

        assertEquals(expected, actual);
    }

    @Test
    void testDeleteDokterWithInvalidId() {
        Integer id = 1;

        String expected = String.format("Tidak ada dokter dengan id %s", id);
        when(dokterRepository.findById(id)).thenReturn(Optional.empty());

        String actual = dokterService.deleteDokter(id);

        assertEquals(expected, actual);
    }

}
