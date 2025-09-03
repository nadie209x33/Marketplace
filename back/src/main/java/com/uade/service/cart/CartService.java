package com.uade.service.cart;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.dto.cart.CartItem;
import com.uade.repository.CarritoRepository;
import com.uade.repository.ListRepository;
import com.uade.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ListRepository listRepository;

    @Transactional
    public getCurrentCart (Integer userId){

        private Integer listID = carritoRepository.findByUser(user)



    }
    



}
