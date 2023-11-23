package com.asyraf.hospital.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asyraf.hospital.dto.MenuResponse;
import com.asyraf.hospital.entity.Menu;
import com.asyraf.hospital.repository.MenuRepository;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuResponse> getAllMenu() {
        List<Menu> menuList = menuRepository.findAll();
        List<MenuResponse> response = new ArrayList<>();
        for (Menu menu: menuList) {
            response.add(MenuResponse.builder()
                    .id(menu.getId())
                    .namaLayanan(menu.getNamaLayanan())
                    .urlImage(menu.getUrlImage())
                    .build());
        }
        return response;
        
    }

}
