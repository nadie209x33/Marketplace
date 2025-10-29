package com.uade.back.controller;

import com.uade.back.dto.cupon.CrearCuponRequest;
import com.uade.back.dto.cupon.CuponPageResponse;
import com.uade.back.dto.cupon.ValidarCuponRequest;
import com.uade.back.dto.cupon.ValidarCuponResponse;
import com.uade.back.entity.Cupon;
import com.uade.back.service.cupon.CuponService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cupones")
@RequiredArgsConstructor
public class CuponController {

    private final CuponService cuponService;

    @PostMapping
    public ResponseEntity<Cupon> crearCupon(
        @RequestBody CrearCuponRequest request
    ) {
        return ResponseEntity.ok(cuponService.crearCupon(request));
    }

    @GetMapping
    public ResponseEntity<CuponPageResponse> getAllCupones(
        @RequestParam(required = false) String codigo,
        @RequestParam(required = false) Boolean activo,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(
            cuponService.getAllCupones(codigo, activo, page, size)
        );
    }

    @PostMapping("/validar")
    public ResponseEntity<ValidarCuponResponse> validarCupon(
        @RequestBody ValidarCuponRequest request
    ) {
        Optional<Cupon> cuponOpt = cuponService.validarCupon(
            request.getCodigo()
        );
        if (cuponOpt.isPresent()) {
            return ResponseEntity.ok(
                new ValidarCuponResponse(
                    true,
                    cuponOpt.get().getPorcentajeDescuento()
                )
            );
        } else {
            return ResponseEntity.ok(new ValidarCuponResponse(false, null));
        }
    }
}
