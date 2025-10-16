package com.uade.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.back.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUserInfo_UserInfoId(Integer userInfoId);

    Optional<Usuario> findByUserInfo_Mail(String mail);

}
