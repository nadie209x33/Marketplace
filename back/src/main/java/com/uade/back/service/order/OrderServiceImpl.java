package com.uade.back.service.order;

import com.uade.back.dto.order.OrderDTO;
import com.uade.back.dto.order.OrderItemDTO;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.back.dto.order.CreateOrderRequest;
import com.uade.back.dto.order.OrderIdRequest;
import com.uade.back.dto.order.OrderResponse;
import com.uade.back.entity.Address;
import com.uade.back.entity.Carro;
import com.uade.back.entity.Delivery;
import com.uade.back.entity.Inventario;
import com.uade.back.entity.Pago;
import com.uade.back.entity.Pedido;
import com.uade.back.entity.Usuario;
import com.uade.back.entity.enums.OrderStatus;
import com.uade.back.entity.enums.PaymentStatus;
import com.uade.back.repository.AddressRepository;
import com.uade.back.repository.CarritoRepository;
import com.uade.back.repository.DeliveryRepository;
import com.uade.back.repository.InventarioRepository;
import com.uade.back.repository.PagoRepository;
import com.uade.back.repository.PedidoRepository;
import com.uade.back.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UsuarioRepository usuarioRepository;
    private final CarritoRepository carritoRepository;
    private final InventarioRepository inventarioRepository;
    private final AddressRepository addressRepository;
    private final DeliveryRepository deliveryRepository;
    private final PedidoRepository pedidoRepository;
    private final PagoRepository pagoRepository;

    private Usuario getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByUserInfo_Mail(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in database"));
    }

    @Override
    @Transactional
    public OrderResponse create(CreateOrderRequest request) {
        
        
        Usuario user = getCurrentUser();
        if (!user.getActive()) {
            throw new RuntimeException("User is not active.");
        }
        Carro cart = carritoRepository.findByUser(user)
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("User does not have a cart."));

        
                
        java.util.List<com.uade.back.entity.List> cartItems = cart.getItems();
        
        if (cartItems.isEmpty()) {
             throw new RuntimeException("Cannot create an order from an empty cart.");
        }

        double total = 0.0;
        for (com.uade.back.entity.List item : cartItems) {
            if (item.getItem() == null) continue; 
            

            if (item.getQuantity() > item.getItem().getQuantity()) {
                throw new RuntimeException("Insufficient stock for item: " + item.getItem().getName());
            }
            
            

            total += item.getQuantity() * item.getItem().getPrice();
        }

        
        
        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found."));
        
        boolean isUserAddress = user.getUserInfo().getAddresses().stream()
                .anyMatch(a -> a.getAddressId().equals(request.getAddressId()));
        
        if (!isUserAddress) {
            throw new RuntimeException("Address does not belong to the user.");
        }

        Delivery delivery = deliveryRepository.findByAddress(address)
                .orElseGet(() -> deliveryRepository.save(Delivery.builder().address(address).provider("Standard").build()));

        Pedido newPedido = Pedido.builder()
                .usuario(user)
                .delivery(delivery)
                .status(OrderStatus.PLACED)
                .build();
        
        List<com.uade.back.entity.List> orderItems = cartItems.stream()
            .map(cartItem -> com.uade.back.entity.List.builder()
                .item(cartItem.getItem())
                .quantity(cartItem.getQuantity())
                .build())
            .collect(Collectors.toList());
        newPedido.setItems(orderItems);
        
        Pedido savedPedido = pedidoRepository.save(newPedido);

        
        

        Pago newPago = Pago.builder()
                .pedido(savedPedido)
                .monto((int) total)
                .medio(request.getPaymentMethod())
                .timestamp(Instant.now())
                .status(PaymentStatus.WAITING)
                .txId(0)
                .build();
        pagoRepository.save(newPago);

        
        
        cart.getItems().clear();
        carritoRepository.save(cart);

        
        
        return toOrderResponse(savedPedido, newPago);
    }

    private OrderResponse toOrderResponse(Pedido pedido, Pago pago) {
        java.util.List<com.uade.back.entity.List> items = pedido.getItems();
        
        java.util.List<OrderResponse.Item> responseItems = items.stream()
            .filter(item -> item.getItem() != null)
            .map(item -> OrderResponse.Item.builder()
                .productId(item.getItem().getItemId())
                .name(item.getItem().getName())
                .quantity(item.getQuantity())
                .price(item.getItem().getPrice())
                .lineTotal(item.getQuantity() * item.getItem().getPrice())
                .build()
            ).collect(java.util.stream.Collectors.toList());

        return OrderResponse.builder()
            .id(pedido.getPedidoId())
            .status(pedido.getStatus().name())
            .total(pago.getMonto().doubleValue())
            .items(responseItems)
            .build();
    }

    @Override
    public OrderResponse getById(OrderIdRequest request) {
        Pedido pedido = pedidoRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + request.id()));
        
        
                
        Pago pago = pagoRepository.findByPedido(pedido).orElseThrow();

        return toOrderResponse(pedido, pago);
    }

    @Override
    public List<OrderDTO> getMyOrders() {
        Usuario user = getCurrentUser();
        List<Pedido> pedidos = pedidoRepository.findByUsuario(user);

        return pedidos.stream()
                .map(this::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(this::toOrderDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO toOrderDTO(Pedido pedido) {
        Pago pago = pagoRepository.findByPedido(pedido)
                .orElseThrow(() -> new RuntimeException("Pago not found for pedido id: " + pedido.getPedidoId()));

        List<OrderItemDTO> itemDTOs = pedido.getItems().stream()
                .map(item -> OrderItemDTO.builder()
                        .productName(item.getItem().getName())
                        .quantity(item.getQuantity())
                        .price(item.getItem().getPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .orderId(pedido.getPedidoId())
                .userEmail(pedido.getUsuario().getUsername())
                .deliveryProvider(pedido.getDelivery().getProvider())
                .deliveryAddress(pedido.getDelivery().getAddress().getStreet())
                .orderStatus(pedido.getStatus())
                .paymentStatus(pago.getStatus())
                .paymentMethod(pago.getMedio())
                .total(pago.getMonto().doubleValue())
                .items(itemDTOs)
                .paymentId(pago.getPagoId())
                .build();
    }

    @Override
    @Transactional
    public void updatePaymentStatus(Integer pagoId, String newStatus) {
        
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + pagoId));

        PaymentStatus paymentStatus = PaymentStatus.valueOf(newStatus.toUpperCase());
        pago.setStatus(paymentStatus);
        
        
        Pedido pedido = pago.getPedido();
        if (paymentStatus == PaymentStatus.SUCCESS) {
            pedido.setStatus(OrderStatus.START_DELIVERY);

            
            java.util.List<com.uade.back.entity.List> items = pedido.getItems();
            for (com.uade.back.entity.List item : items) {
                 if (item.getItem() == null) continue;
                 Inventario inventario = item.getItem();
                 int newQuantity = inventario.getQuantity() - item.getQuantity();
                 if (newQuantity < 0) {
                     throw new IllegalStateException("Stock cannot be negative for item: " + inventario.getName());
                 }
                 inventario.setQuantity(newQuantity);
                 inventarioRepository.save(inventario);
            }

        } else if (paymentStatus == PaymentStatus.FAILED) {
            pedido.setStatus(OrderStatus.PLACED);
        }
        
        pagoRepository.save(pago);
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void updateDeliveryStatus(Integer orderId, String newStatus) {
        Pedido pedido = pedidoRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        OrderStatus orderStatus = OrderStatus.valueOf(newStatus.toUpperCase());
        pedido.setStatus(orderStatus);
        pedidoRepository.save(pedido);
    }
}
