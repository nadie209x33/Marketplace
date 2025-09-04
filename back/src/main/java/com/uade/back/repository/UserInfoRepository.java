package com.uade.back.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.back.entity.UserInfo;


@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByMail(String mail);

}
