package com.uade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.entity.Otp;

@Repository
public interface OTPRepository extends JpaRepository<Otp, Integer> {

}
