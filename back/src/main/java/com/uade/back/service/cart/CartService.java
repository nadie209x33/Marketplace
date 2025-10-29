package com.uade.back.service.cart;

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
import com.uade.back.service.cupon.CuponService;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ListRepository listRepository;
    private final InventarioRepository inventarioRepository;
    private final CuponService cuponService;

    private Usuario getCurrentUser() {
        String username = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();
        return usuarioRepository
            .findByUserInfo_Mail(username)
            .orElseThrow(() ->
                new RuntimeException("Authenticated user not found in database")
            );
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

        Inventario productInventory = inventarioRepository
            .findById(request.getProductId())
            .orElseThrow(() ->
                new RuntimeException(
                    "Product not found with id: " + request.getProductId()
                )
            );

        if (productInventory.getQuantity() < request.getQuantity()) {
            throw new RuntimeException(
                "Insufficient stock for product: " + productInventory.getName()
            );
        }

        com.uade.back.entity.List existingItem = cart
            .getItems()
            .stream()
            .filter(item -> item.getItem().equals(productInventory))
            .findFirst()
            .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(
                existingItem.getQuantity() + request.getQuantity()
            );
        } else {
            com.uade.back.entity.List newItem =
                com.uade.back.entity.List.builder()
                    .item(productInventory)
                    .quantity(request.getQuantity())
                    .carro(cart)
                    .build();
            cart.getItems().add(newItem);
        }

        carritoRepository.save(cart);
        return createCartResponse(cart);
    }

    @Transactional
    public CartResponse updateItem(Integer itemId, UpdateItemRequest request) {
        if (request.getQuantity() != null && request.getQuantity() == 0) {
            return removeItem(itemId);
        }

        Usuario user = getCurrentUser();
        Carro cart = getOrCreateCart(user);

        com.uade.back.entity.List itemToUpdate = cart
            .getItems()
            .stream()
            .filter(item -> item.getTlistId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        if (itemToUpdate.getItem().getQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        itemToUpdate.setQuantity(request.getQuantity());
        carritoRepository.save(cart);

        return createCartResponse(cart);
    }

    @Transactional
    public CartResponse removeItem(Integer itemId) {
        Usuario user = getCurrentUser();
        Carro cart = getOrCreateCart(user);

        cart.getItems().removeIf(item -> item.getTlistId().equals(itemId));
        carritoRepository.save(cart);

        return createCartResponse(cart);
    }

    @Transactional
    public CartResponse clear() {
        Usuario user = getCurrentUser();
        Carro cart = getOrCreateCart(user);
        cart.getItems().clear();
        carritoRepository.save(cart);
        return createCartResponse(cart);
    }

    @Transactional
    public CartResponse aplicarCupon(
        com.uade.back.dto.cart.AplicarCuponRequest request
    ) {
        Usuario user = getCurrentUser();
        Carro cart = getOrCreateCart(user);

        if (request.getCodigo() == null || request.getCodigo().isEmpty()) {
            cart.setCupon(null);
            carritoRepository.save(cart);
            return createCartResponse(cart);
        }

        com.uade.back.entity.Cupon cupon = cuponService
            .validarCupon(request.getCodigo())
            .orElseThrow(() ->
                new RuntimeException("El cupón no es válido o ha expirado.")
            );

        cart.setCupon(cupon);
        carritoRepository.save(cart);

        return createCartResponse(cart);
    }

    private Carro getOrCreateCart(Usuario user) {
        return carritoRepository
            .findByUser(user)
            .stream()
            .findFirst()
            .orElseGet(() -> {
                Carro newCart = Carro.builder().user(user).build();
                return carritoRepository.save(newCart);
            });
    }

    private CartResponse createCartResponse(Carro cart) {
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            return CartResponse.builder()
                .id(cart.getCarro_ID())
                .items(Collections.emptyList())
                .total(Double.valueOf(0))
                .build();
        }

        java.util.List<CartItem> cartItems = cart
            .getItems()
            .stream()
            .filter(item -> item.getItem() != null)
            .map(item -> {
                Integer imageId = null;
                if (
                    item.getItem().getImages() != null &&
                    !item.getItem().getImages().isEmpty()
                ) {
                    imageId = item.getItem().getImages().get(0).getImgId();
                }
                return CartItem.builder()
                    .id(item.getTlistId())
                    .productId(item.getItem().getItemId())
                    .name(item.getItem().getName())
                    .quantity(item.getQuantity())
                    .price(item.getItem().getPrice())
                    .lineTotal(item.getQuantity() * item.getItem().getPrice())
                    .imageId(imageId)
                    .build();
            })
            .collect(Collectors.toList());

        Double subTotal = cartItems
            .stream()
            .mapToDouble(CartItem::getLineTotal)
            .sum();

        Double total = subTotal;
        Double discount = 0.0;
        String couponCode = null;

        if (cart.getCupon() != null) {
            com.uade.back.entity.Cupon cupon = cart.getCupon();
            discount = subTotal * (cupon.getPorcentajeDescuento() / 100);
            total = subTotal - discount;
            couponCode = cupon.getCodigo();
        }

        return CartResponse.builder()
            .id(cart.getCarro_ID())
            .items(cartItems)
            .subTotal(subTotal)
            .discount(discount)
            .total(total)
            .couponCode(couponCode)
            .build();
    }
}
