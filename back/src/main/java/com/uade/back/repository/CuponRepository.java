package com.uade.back.repository;

import com.uade.back.entity.Cupon;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Integer> {
    Optional<Cupon> findByCodigo(String codigo);

    @Query(
        "SELECT c FROM Cupon c WHERE " +
            "(:codigo IS NULL OR LOWER(c.codigo) LIKE LOWER(CONCAT('%', :codigo, '%'))) AND " +
            "(:activo IS NULL OR c.activo = :activo)"
    )
    Page<Cupon> search(
        @Param("codigo") String codigo,
        @Param("activo") Boolean activo,
        Pageable pageable
    );
}
