package com.uade.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.back.entity.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
}
