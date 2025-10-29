package com.uade.back.service.cupon;

import com.uade.back.dto.cupon.CrearCuponRequest;
import com.uade.back.dto.cupon.CuponPageResponse;
import com.uade.back.dto.cupon.CuponResponse;
import com.uade.back.entity.Cupon;
import com.uade.back.repository.CuponRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CuponService {

    private final CuponRepository cuponRepository;

    @Transactional
    public Cupon crearCupon(CrearCuponRequest request) {
        if (cuponRepository.findByCodigo(request.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("El código de cupón ya existe.");
        }

        Cupon cupon = Cupon.builder()
            .codigo(request.getCodigo())
            .porcentajeDescuento(request.getPorcentajeDescuento())
            .fechaExpiracion(request.getFechaExpiracion())
            .usosMaximos(request.getUsosMaximos())
            .build();

        return cuponRepository.save(cupon);
    }

    @Transactional(readOnly = true)
    public Optional<Cupon> validarCupon(String codigo) {
        Optional<Cupon> cuponOpt = cuponRepository.findByCodigo(codigo);

        if (cuponOpt.isEmpty()) {
            return Optional.empty();
        }

        Cupon cupon = cuponOpt.get();

        if (
            !cupon.getActivo() ||
            cupon.getFechaExpiracion().isBefore(Instant.now()) ||
            cupon.getUsosActuales() >= cupon.getUsosMaximos()
        ) {
            return Optional.empty();
        }

        return cuponOpt;
    }

    @Transactional(readOnly = true)
    public CuponPageResponse getAllCupones(
        String codigo,
        Boolean activo,
        int page,
        int size
    ) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
            page,
            size
        );
        Page<Cupon> results = cuponRepository.search(codigo, activo, pageable);
        List<CuponResponse> cupones = results
            .getContent()
            .stream()
            .map(this::toCuponResponse)
            .collect(Collectors.toList());

        return new CuponPageResponse(cupones, results.getTotalPages());
    }

    private CuponResponse toCuponResponse(Cupon cupon) {
        return CuponResponse.builder()
            .id(cupon.getCuponId())
            .codigo(cupon.getCodigo())
            .porcentajeDescuento(cupon.getPorcentajeDescuento())
            .fechaExpiracion(cupon.getFechaExpiracion())
            .usosMaximos(cupon.getUsosMaximos())
            .usosActuales(cupon.getUsosActuales())
            .activo(cupon.getActivo())
            .build();
    }
}
