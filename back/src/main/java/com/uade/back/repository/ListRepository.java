package com.uade.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.back.entity.List;

@Repository()
public interface ListRepository extends JpaRepository<List, Integer> {

}
