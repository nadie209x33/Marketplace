package com.uade.service;

import org.springframework.stereotype.Service;

import com.uade.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsuarioRepository usuarioRepository;

}
