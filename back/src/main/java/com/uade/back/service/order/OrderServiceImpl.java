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
import com.uade.back.entity.Role;
import org.springframework.security.access.AccessDeniedException;
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
                .creationTimestamp(Instant.now())
                .build();
        
        List<com.uade.back.entity.List> orderItems = cartItems.stream()
            .map(cartItem -> com.uade.back.entity.List.builder()
                .item(cartItem.getItem())
                .quantity(cartItem.getQuantity())
                .build())
            .collect(Collectors.toList());
        newPedido.setItems(orderItems);

        Pago newPago = Pago.builder()
                .pedido(newPedido)
                .monto((int) total)
                .medio(request.getPaymentMethod())
                .timestamp(Instant.now())
                .status(PaymentStatus.WAITING)
                .txId(0)
                .build();
        newPedido.getPagos().add(newPago);

        Pedido savedPedido = pedidoRepository.save(newPedido);

        
        
        cart.getItems().clear();
        carritoRepository.save(cart);

        
        
        return toOrderResponse(savedPedido, savedPedido.getPagos().get(savedPedido.getPagos().size() - 1));
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

        Usuario currentUser = getCurrentUser();
        if (!pedido.getUsuario().getUser_ID().equals(currentUser.getUser_ID()) && !currentUser.getAuthLevel().equals(Role.ADMIN)) {
            throw new AccessDeniedException("You are not authorized to access this order.");
        }

        Pago pago = pedido.getPagos().stream().max((p1, p2) -> p1.getTimestamp().compareTo(p2.getTimestamp())).orElseThrow();

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
        Pago pago = pedido.getPagos().stream().max((p1, p2) -> p1.getTimestamp().compareTo(p2.getTimestamp()))
                .orElseThrow(() -> new RuntimeException("Pago not found for pedido id: " + pedido.getPedidoId()));

        List<OrderItemDTO> itemDTOs = pedido.getItems().stream()
                .map(item -> OrderItemDTO.builder()
                        .productName(item.getItem().getName())
                        .quantity(item.getQuantity())
                        .price(item.getItem().getPrice())
                        .build())
                .collect(Collectors.toList());

        Address address = pedido.getDelivery().getAddress();
        String fullAddress = address.getStreet() + ", " + address.getApt() + ", " + address.getPostalCode() + ", " + address.getOthers();

        return OrderDTO.builder()
                .orderId(pedido.getPedidoId())
                .userEmail(pedido.getUsuario().getUsername())
                .deliveryProvider(pedido.getDelivery().getProvider())
                .deliveryAddress(fullAddress)
                .orderStatus(pedido.getStatus())
                .paymentStatus(pago.getStatus())
                .paymentMethod(pago.getMedio())
                .total(pago.getMonto().doubleValue())
                .items(itemDTOs)
                .paymentId(pago.getPagoId())
                .orderTimestamp(pedido.getCreationTimestamp())
                .paymentTimestamp(pago.getTimestamp())
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

    @Override
    @Transactional
    public OrderResponse retryPayment(Integer orderId, com.uade.back.dto.order.RetryPaymentRequest request) {
        Pedido pedido = pedidoRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        Usuario currentUser = getCurrentUser();
        if (!pedido.getUsuario().getUser_ID().equals(currentUser.getUser_ID())) {
            throw new AccessDeniedException("You are not authorized to access this order.");
        }

        Pago latestPago = pedido.getPagos().stream().max((p1, p2) -> p1.getTimestamp().compareTo(p2.getTimestamp()))
                .orElseThrow(() -> new RuntimeException("Payment not found for order id: " + orderId));

        if (latestPago.getStatus() != PaymentStatus.FAILED) {
            throw new IllegalStateException("Payment retry is only allowed for failed payments.");
        }

        Pago newPago = Pago.builder()
                .pedido(pedido)
                .monto(latestPago.getMonto())
                .medio(request.getPaymentMethod())
                .timestamp(Instant.now())
                .status(PaymentStatus.WAITING)
                .txId(0)
                .build();
        pedido.getPagos().add(newPago);

        Pedido savedPedido = pedidoRepository.save(pedido);

        return toOrderResponse(savedPedido, newPago);
    }

    @Override
    public List<com.uade.back.dto.order.PaymentDTO> getOrderPayments(Integer orderId) {
        Pedido pedido = pedidoRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        return pedido.getPagos().stream()
                .map(this::toPaymentDTO)
                .collect(Collectors.toList());
    }

    private com.uade.back.dto.order.PaymentDTO toPaymentDTO(Pago pago) {
        return com.uade.back.dto.order.PaymentDTO.builder()
                .paymentId(pago.getPagoId())
                .amount(pago.getMonto())
                .paymentMethod(pago.getMedio())
                .timestamp(pago.getTimestamp())
                .status(pago.getStatus())
                .transactionId(pago.getTxId())
                .build();
    }
}
