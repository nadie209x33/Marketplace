package com.uade.back.service.cart;

import com.uade.back.dto.cart.AddItemRequest;
import com.uade.back.dto.cart.CartItem;
import com.uade.back.dto.cart.CartResponse;
import com.uade.back.dto.cart.UpdateItemRequest;
import com.uade.back.entity.Carro;
import com.uade.back.entity.Inventario;
import com.uade.back.entity.List;
import com.uade.back.entity.Usuario;
import com.uade.back.repository.CarritoRepository;
import com.uade.back.repository.InventarioRepository;
import com.uade.back.repository.ListRepository;
import com.uade.back.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ListRepository listRepository;
    private final InventarioRepository inventarioRepository;

    private Usuario getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByUserInfo_Mail(username)
                .orElseThrow();
    }

    @Transactional
    public CartResponse getCurrentCart() {
        Usuario user = getCurrentUser();
        Carro cart = getOrCreateCart(user);
        return createCartResponse(cart);
    }

    @Transactional
    public CartResponse addItem(AddItemRequest request) {
        Usuario user = getCurrentUser();
        Carro cart = getOrCreateCart(user);

        Optional<Inventario> productInventory = inventarioRepository.findById(request.getProductId());
        
        if (productInventory.isEmpty() || productInventory.get().getQuantity() < request.getQuantity()) {
            throw new RuntimeException();
        }

        Optional<List> existingItem = listRepository.findByListIdAndItem(cart.getList().getListId(), productInventory.get());

        if (existingItem.isPresent()) {
            List item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            listRepository.save(item);
        } else {
            List newItem = new List();
            newItem.setListId(cart.getList().getListId());
            newItem.setItem(productInventory.get());
            newItem.setQuantity(request.getQuantity());
            listRepository.save(newItem);
        }

        return createCartResponse(cart);
    }

    @Transactional
    public CartResponse updateItem(Long itemId, UpdateItemRequest request) {
        Usuario user = getCurrentUser();
        Carro cart = getOrCreateCart(user);

        Optional<List> itemToUpdate = listRepository.findById(itemId.intValue());
        if (itemToUpdate.isEmpty() || !itemToUpdate.get().getListId().equals(cart.getList().getListId())) {
            throw new RuntimeException();
        }

        List item = itemToUpdate.get();
        if (item.getItem().getQuantity() < request.getQuantity()) {
            throw new RuntimeException();
        }

        item.setQuantity(request.getQuantity());
        listRepository.save(item);

        return createCartResponse(cart);
    }

    @Transactional
    public CartResponse removeItem(Long itemId) {
        Usuario user = getCurrentUser();
        Carro cart = getOrCreateCart(user);

        listRepository.deleteById(itemId.intValue());

        return createCartResponse(cart);
    }

    @Transactional
    public CartResponse clear() {
        Usuario user = getCurrentUser();
        Carro cart = getOrCreateCart(user);
        listRepository.deleteAllByListId(cart.getList().getListId());
        return createCartResponse(cart);
    }

    

    private Carro getOrCreateCart(Usuario user) {
        return carritoRepository.findByUser(user).stream().findFirst().orElseGet(() -> {
            Carro newCart = new Carro();
            newCart.setUser(user);
            com.uade.back.entity.List newList = new com.uade.back.entity.List();
            listRepository.save(newList); 
            newCart.setList(newList);
            return carritoRepository.save(newCart);
        });
    }

    private CartResponse createCartResponse(Carro cart) {
        if (cart.getList() == null) {
            return new CartResponse(cart.getCarro_ID(), Collections.emptyList(), 0);
        }

        java.util.List<List> items = listRepository.findAllByListId(cart.getList().getListId());
        java.util.List<CartItem> cartItems = items.stream().map(item -> {
            CartItem dto = new CartItem();
            dto.setId(item.getTlistId());
            dto.setName(item.getItem().getName());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getItem().getPrice());
            dto.setLineTotal(item.getQuantity() * item.getItem().getPrice());
            return dto;
        }).collect(Collectors.toList());

        Integer totalPrice = cartItems.stream()
                              .mapToInt(item -> item.getLineTotal())
                              .sum();

        return new CartResponse(cart.getCarro_ID(), cartItems, totalPrice);
    }
}
