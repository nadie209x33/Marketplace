package com.uade.back.service.security;

import java.security.SecureRandom;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.back.dto.auth.AuthenticationRequest;
import com.uade.back.dto.auth.AuthenticationResponse;
import com.uade.back.dto.user.NewUserDTO;
import com.uade.back.entity.Otp;
import com.uade.back.entity.UserInfo;
import com.uade.back.entity.Usuario;
import com.uade.back.repository.OTPRepository;
import com.uade.back.repository.UserInfoRepository;
import com.uade.back.repository.UsuarioRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final UsuarioRepository usuarioRepository;
        private final UserInfoRepository userInfoRepository;
        private final OTPRepository otpRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;


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
        public AuthenticationResponse register(NewUserDTO info) {

                
                UserInfo nuevoUsuarioInfo = UserInfo.builder()
                .confirmMail(false)
                .firstName(info.getFirstName())
                .lastName(info.getLastName()).mail(info.getMail()).build();

                UserInfo midui = userInfoRepository.save(nuevoUsuarioInfo);

                Otp nuevoOtp = Otp.builder().otp(this.otpGen(8)).timestamp(Instant.now()).build();

                Otp midotp = otpRepository.save(nuevoOtp);

                Usuario nuevoUsuario = Usuario.builder()
                .passkey(passwordEncoder.encode(info.getPasskey()))
                .otp(midotp)
                .authLevel(com.uade.back.entity.Role.USER)
                .active(true)
                .userInfo(midui)
                .build();

                usuarioRepository.save(nuevoUsuario);

                var jwtToken = jwtService.generateToken(nuevoUsuario);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
                }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                var userInfo = userInfoRepository.findByMail(request.getEmail())
                                .orElseThrow();

                var user = usuarioRepository.findByUserInfo_UserInfoId(userInfo.getUserInfoId())
                                .orElseThrow();

                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }
}
