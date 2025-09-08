package com.uade.back.service.user;

import org.springframework.stereotype.Service;

import com.uade.back.entity.Role;
import com.uade.back.entity.Usuario;
import com.uade.back.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void upgradeToAdmin(Integer userId) {
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setAuthLevel(Role.ADMIN);
        usuarioRepository.save(user);
    }

    @Override
    public void downgradeToUser(Integer userId) {
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setAuthLevel(Role.USER);
        usuarioRepository.save(user);
    }
}
