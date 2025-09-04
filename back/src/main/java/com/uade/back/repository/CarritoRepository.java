package com.uade.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.back.entity.Carro;
import com.uade.back.entity.Usuario;


@Repository()
public interface CarritoRepository extends JpaRepository<Carro, Integer> {

    List<Carro> findByUser(Usuario user);

}
