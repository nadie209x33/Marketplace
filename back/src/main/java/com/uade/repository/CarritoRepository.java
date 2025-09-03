package com.uade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.entity.Carro;
import com.uade.entity.Usuario;


@Repository()
public interface CarritoRepository extends JpaRepository<Carro, Integer> {

    List<Carro> findByUser(Usuario user);

}
