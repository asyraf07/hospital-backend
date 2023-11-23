package com.asyraf.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asyraf.hospital.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
