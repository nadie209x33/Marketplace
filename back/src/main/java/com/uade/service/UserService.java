package com.uade.service;

import java.security.SecureRandom;
import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.dto.UsuarioNuevoDTO;
import com.uade.entity.Otp;
import com.uade.entity.UserInfo;
import com.uade.entity.Usuario;
import com.uade.repository.OTPRepository;
import com.uade.repository.UserInfoRepository;
import com.uade.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsuarioRepository usuarioRepository;
    private final UserInfoRepository userInfoRepository;
    private final OTPRepository otpRepository;

    private String otpGen(int n) {

        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int value = random.nextInt(caracteres.length());
            otp.append(caracteres.charAt(value));
        }
        return otp.toString();
    }


    @Transactional
    public UsuarioNuevoDTO crearNuevoUsuario(UsuarioNuevoDTO info){
        
        UserInfo nuevoUsuarioInfo = UserInfo.builder()
        .confirmMail(false)
        .firstName(info.getFirstName())
        .lastName(info.getLastName()).mail(info.getMail()).build();

        UserInfo midui = userInfoRepository.save(nuevoUsuarioInfo);

        Otp nuevoOtp = Otp.builder().otp(this.otpGen(8)).timestamp(Instant.now()).build();

        Otp midotp = otpRepository.save(nuevoOtp);

        Usuario nuevoUsuario = Usuario.builder()
        .passkey(info.getPasskey())
        .otp(midotp)
        .authLevel(0)
        .active(true)
        .userInfo(midui)
        .build();

        usuarioRepository.save(nuevoUsuario);

        return info;


    }

}
