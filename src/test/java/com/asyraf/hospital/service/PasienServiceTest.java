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

import com.asyraf.hospital.dto.PasienRequest;
import com.asyraf.hospital.dto.PasienResponse;
import com.asyraf.hospital.entity.Pasien;
import com.asyraf.hospital.entity.User;
import com.asyraf.hospital.repository.PasienRepository;
import com.asyraf.hospital.repository.UserRepository;

class PasienServiceTest {

    @InjectMocks
    private PasienService pasienService;

    @Mock
    private PasienRepository pasienRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    public Pasien pasienGenerator(Integer id, User user) {
        Pasien pasien = new Pasien();
        pasien.setId(id);
        pasien.setUser(user);
        return pasien;
    }

    public User userGenerator(Integer id, String nama, String noHp) {
        User user = new User();
        user.setId(id);
        user.setNama(nama);
        user.setNoHP(noHp);
        return user;
    }

    public PasienRequest requestGenerator(Integer userId) {
        return PasienRequest.builder()
                .userId(userId)
                .build();
    }

    @Test
    void testGetAllPasien() {
        User user1 = userGenerator(1, "Asyraf", "08111226664");
        Pasien pasien1 = pasienGenerator(1, user1);

        User user2 = userGenerator(2, "Budi", "08111223334");
        Pasien pasien2 = pasienGenerator(2, user2);

        List<Pasien> expectedList = new ArrayList<>();
        expectedList.add(pasien1);
        expectedList.add(pasien2);

        when(pasienRepository.findAll()).thenReturn(expectedList);

        List<PasienResponse> actual = pasienService.getAllPasien();

        assertEquals(expectedList.size(), actual.size());
    }

    @Test
    void testGetOnePasienWithValidId() {
        Integer id = 1;
        User user = userGenerator(1, "Asyraf", "08111226664");
        Pasien expected = pasienGenerator(1, user);
        when(pasienRepository.findById(id)).thenReturn(Optional.of(expected));

        PasienResponse actual = pasienService.getPasien(id);

        assertEquals(expected.getUser().getNama(), actual.getNama());
    }

    @Test
    void testGetOnePasienWithInvalidId() {
        Integer id = 1;
        ServiceException expectedException = new ServiceException("Tidak ada pasien dengan ID ini!");
        when(pasienRepository.findById(id)).thenThrow(expectedException);

        assertThrows(expectedException.getClass(), () -> pasienService.getPasien(id));
    }

    @Test
    void testPostPasienWithValidUserId() {
        Integer id = 1;
        User user = userGenerator(1, "Asyraf", "08111226664");
        Pasien pasien = pasienGenerator(id, user);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(pasienRepository.save(pasien)).thenReturn(pasien);

        PasienRequest request = requestGenerator(user.getId());
        PasienResponse actual = pasienService.addPasien(request);

        assertEquals(pasien.getUser().getNama(), actual.getNama());
        assertEquals(pasien.getUser().getNoHP(), actual.getNoHp());
        assertEquals(pasien.getUser().getId(), actual.getUserId());
    }

    @Test
    void testPostPasienWithInvalidUserId() {
        Integer id = 1;
        User user = userGenerator(1, "Asyraf", "08111226664");

        ServiceException expected = new ServiceException("Tidak ada user dengan ID ini!");
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        PasienRequest request = requestGenerator(user.getId());

        assertThrows(expected.getClass(), () -> pasienService.addPasien(request));
    }

    @Test
    void testPutPasienWithValidUserIdAndPasienId() {
        Integer id = 1;
        User user1 = userGenerator(1, "Asyraf", "08111226664");
        User user2 = userGenerator(2, "Budi", "08111223334");

        Pasien pasien = pasienGenerator(id, user1);

        Pasien updated = pasienGenerator(id, user2);

        when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));
        when(pasienRepository.findById(id)).thenReturn(Optional.of(pasien));
        when(pasienRepository.save(pasien)).thenReturn(updated);

        PasienRequest request = requestGenerator(user2.getId());
        PasienResponse actual = pasienService.updatePasien(id, request);

        assertEquals(pasien.getUser().getNama(), actual.getNama());
        assertEquals(pasien.getUser().getNoHP(), actual.getNoHp());
        assertEquals(pasien.getUser().getId(), actual.getUserId());
    }

    @Test
    void testPutPasienWithInvalidUserId() {
        Integer id = 1;
        User user = userGenerator(1, "Asyraf", "08111226664");
        Pasien pasien = pasienGenerator(1, user);

        ServiceException expected = new ServiceException("Tidak ada user dengan ID ini!");
        when(pasienRepository.findById(id)).thenReturn(Optional.of(pasien));
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        PasienRequest request = requestGenerator(user.getId());

        assertThrows(expected.getClass(), () -> pasienService.addPasien(request));
    }

    @Test
    void testPutPasienWithInvalidPasienId() {
        Integer id = 1;
        User user = userGenerator(1, "Asyraf", "08111226664");

        ServiceException expected = new ServiceException("Tidak ada user dengan ID ini!");
        when(pasienRepository.findById(id)).thenReturn(Optional.empty());

        PasienRequest request = requestGenerator(user.getId());

        assertThrows(expected.getClass(), () -> pasienService.addPasien(request));
    }

    @Test
    void testDeletePasienWithValidId() {
        Integer id = 1;
        User user = userGenerator(1, "Asyraf", "08111226664"); Pasien pasien = pasienGenerator(1, user);

        String expected = String.format("Pasien dengan id %s berhasil dihapus!", id);
        when(pasienRepository.findById(id)).thenReturn(Optional.of(pasien));

        String actual = pasienService.deletePasien(id);

        assertEquals(expected, actual);
    }

    @Test
    void testDeletePasienWithInvalidId() {
        Integer id = 1;

        String expected = String.format("Tidak ada pasien dengan id %s", id);
        when(pasienRepository.findById(id)).thenReturn(Optional.empty());

        String actual = pasienService.deletePasien(id);

        assertEquals(expected, actual);
    }

}
