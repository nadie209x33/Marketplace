package com.uade.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.back.entity.Inventario;
import com.uade.back.entity.List;

@Repository()
public interface ListRepository extends JpaRepository<List, Integer> {
    Optional<List> findByListIdAndItem(Integer listId, Inventario item);
    java.util.List<List> findAllByListId(Integer listId);
    void deleteAllByListId(Integer listId);
}
