package com.uade.back.service.cart;

import org.springframework.stereotype.Service;

import com.uade.back.repository.CarritoRepository;
import com.uade.back.repository.ListRepository;
import com.uade.back.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ListRepository listRepository;

  //  @Transactional
 //   public getCurrentCart (Integer userId){

        //private Integer listID = carritoRepository.findByUser(user);



  //  }
    



}
