package com.uade.back.service.cart;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.back.dto.cart.AddItemRequest;
import com.uade.back.dto.cart.CartItem;
import com.uade.back.dto.cart.CartResponse;
import com.uade.back.dto.cart.UpdateItemRequest;
import com.uade.back.entity.Carro;
import com.uade.back.entity.Inventario;
import com.uade.back.entity.Usuario;
import com.uade.back.repository.CarritoRepository;
import com.uade.back.repository.InventarioRepository;
import com.uade.back.repository.ListRepository;
import com.uade.back.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

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
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in database"));
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

        Inventario productInventory = inventarioRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));
        
        if (productInventory.getQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + productInventory.getName());
        }

        com.uade.back.entity.List existingItem = listRepository
                .findByListIdAndItem(cart.getList().getListId(), productInventory)
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            listRepository.save(existingItem);
        } else {
            com.uade.back.entity.List newItem = com.uade.back.entity.List.builder()
                .listId(cart.getList().getListId())
                .item(productInventory)
                .quantity(request.getQuantity())
                .build();
            listRepository.save(newItem);
        }

        return createCartResponse(cart);
    }

    @Transactional
    public CartResponse updateItem(Long itemId, UpdateItemRequest request) {
        if (request.getQuantity() != null && request.getQuantity() == 0) {
            return removeItem(itemId);
        }

        Usuario user = getCurrentUser();
        Carro cart = getOrCreateCart(user);

        com.uade.back.entity.List itemToUpdate = listRepository.findById(itemId.intValue())
            .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        if (!itemToUpdate.getListId().equals(cart.getList().getListId())) {
            throw new RuntimeException("Item does not belong to the current user's cart.");
        }

        if (itemToUpdate.getItem().getQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        itemToUpdate.setQuantity(request.getQuantity());
        listRepository.save(itemToUpdate);

        return createCartResponse(cart);
    }

    @Transactional
    public CartResponse removeItem(Long itemId) {
        Usuario user = getCurrentUser();
        Carro cart = getOrCreateCart(user);
        
        com.uade.back.entity.List itemToRemove = listRepository.findById(itemId.intValue())
            .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        if (!itemToRemove.getListId().equals(cart.getList().getListId())) {
            throw new RuntimeException("Item does not belong to the current user's cart.");
        }

        listRepository.deleteById(itemId.intValue());

        return createCartResponse(cart);
    }

    @Transactional
    public CartResponse clear() {
        Usuario user = getCurrentUser();
        Carro cart = getOrCreateCart(user);
        if (cart.getList() != null && cart.getList().getListId() != null) {
            listRepository.deleteAllByListId(cart.getList().getListId());
        }
        return createCartResponse(cart);
    }
    
    private Carro getOrCreateCart(Usuario user) {
        return carritoRepository.findByUser(user).stream().findFirst().orElseGet(() -> {

            
            com.uade.back.entity.List newList = new com.uade.back.entity.List();
            newList.setQuantity(0);
            com.uade.back.entity.List savedList = listRepository.save(newList); 

            
            savedList.setListId(savedList.getTlistId());
            listRepository.save(savedList);

            
            Carro newCart = Carro.builder()
                .user(user)
                .list(savedList)
                .build();
            return carritoRepository.save(newCart);
        });
    }

    private CartResponse createCartResponse(Carro cart) {
        if (cart.getList() == null || cart.getList().getListId() == null) {
            return CartResponse.builder()
                .id(cart.getCarro_ID())
                .items(Collections.emptyList())
                .total(0)
                .build();
        }

        java.util.List<com.uade.back.entity.List> items = listRepository.findAllByListId(cart.getList().getListId());
        
        java.util.List<CartItem> cartItems = items.stream()
            .filter(item -> item.getItem() != null)
            .map(item -> CartItem.builder()
            .id(item.getTlistId())
            .productId(item.getItem().getItemId())
            .name(item.getItem().getName())
            .quantity(item.getQuantity())
            .price(item.getItem().getPrice())
            .lineTotal(item.getQuantity() * item.getItem().getPrice())
            .build()
        ).collect(Collectors.toList());

        Double totalPrice = cartItems.stream()
                              .mapToDouble(CartItem::getLineTotal)
                              .sum();

        return CartResponse.builder()
            .id(cart.getCarro_ID())
            .items(cartItems)
            .total(totalPrice.intValue())
            .build();
    }
}
