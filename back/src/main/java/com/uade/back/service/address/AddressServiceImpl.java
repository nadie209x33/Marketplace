package com.uade.back.service.address;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.back.dto.address.AddressDto;
import com.uade.back.dto.address.CreateAddressRequest;
import com.uade.back.entity.Address;
import com.uade.back.entity.UserInfo;
import com.uade.back.entity.Usuario;
import com.uade.back.repository.AddressRepository;
import com.uade.back.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional // todas las operaciones de este servicio serán transaccionales
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Si necesitas persistir cambios en el lado dueño (UserInfo), conviene tener el
     * Usuario
     */
    private Usuario getCurrentUsuario() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByUserInfo_Mail(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in database"));
    }

    private UserInfo getCurrentUserInfo() {
        return getCurrentUsuario().getUserInfo();
    }

    @Override
    public AddressDto createAddress(CreateAddressRequest addressRequest) {
        // Obtenemos al usuario y su UserInfo (lado dueño suele ser UserInfo.addresses)
        Usuario usuario = getCurrentUsuario();
        UserInfo userInfo = usuario.getUserInfo();

        Address address = new Address();
        address.setStreet(addressRequest.getStreet());
        address.setApt(addressRequest.getApt());
        address.setPostalCode(addressRequest.getPostalCode());
        address.setOthers(addressRequest.getOthers());
        address.setName(addressRequest.getName());

        // Mantener la relación en ambos lados del ManyToMany
        address.getUsersInfo().add(userInfo);
        userInfo.getAddresses().add(address);

        // Guardar la Address (para obtener ID)...
        Address savedAddress = addressRepository.save(address);
        // ...y MUY IMPORTANTE: guardar el lado dueño (UserInfo dentro de Usuario)
        usuarioRepository.save(usuario);

        return AddressDto.fromEntity(savedAddress);
    }

    @Override
    public List<AddressDto> getAddresses() {
        UserInfo userInfo = getCurrentUserInfo();
        return userInfo.getAddresses().stream()
                .map(AddressDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto getAddressById(Integer addressId) {
        UserInfo userInfo = getCurrentUserInfo();
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        boolean isUserAddress = address.getUsersInfo().stream()
                .anyMatch(ui -> Objects.equals(ui.getUserInfoId(), userInfo.getUserInfoId()));
        if (!isUserAddress) {
            throw new RuntimeException("Address does not belong to the user");
        }

        return AddressDto.fromEntity(address);
    }

    @Override
    public AddressDto updateAddress(Integer addressId, CreateAddressRequest addressRequest) {
        UserInfo userInfo = getCurrentUserInfo();
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        boolean isUserAddress = address.getUsersInfo().stream()
                .anyMatch(ui -> Objects.equals(ui.getUserInfoId(), userInfo.getUserInfoId()));
        if (!isUserAddress) {
            throw new RuntimeException("Address does not belong to the user");
        }

        address.setStreet(addressRequest.getStreet());
        address.setApt(addressRequest.getApt());
        address.setPostalCode(addressRequest.getPostalCode());
        address.setOthers(addressRequest.getOthers());
        address.setName(addressRequest.getName());

        Address updatedAddress = addressRepository.save(address);
        return AddressDto.fromEntity(updatedAddress);
    }

    @Override
    public void deleteAddress(Integer addressId) {
        // Usuario actual + su UserInfo
        Usuario usuario = getCurrentUsuario();
        UserInfo userInfo = usuario.getUserInfo();

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // Validar pertenencia
        boolean isUserAddress = address.getUsersInfo().stream()
                .anyMatch(ui -> Objects.equals(ui.getUserInfoId(), userInfo.getUserInfoId()));
        if (!isUserAddress) {
            throw new RuntimeException("Address does not belong to the user");
        }

        // 1) Corta la relación en la tabla intermedia (garantizado)
        addressRepository.unlinkUserAddress(userInfo.getUserInfoId(), addressId);

        // 2) Limpia colecciones en memoria (consistencia del contexto JPA)
        userInfo.getAddresses().removeIf(a -> Objects.equals(a.getAddressId(), addressId));
        address.getUsersInfo().removeIf(ui -> Objects.equals(ui.getUserInfoId(), userInfo.getUserInfoId()));

        // 3) Si queda huérfana, borra la Address
        if (addressRepository.countLinks(addressId) == 0L) {
            addressRepository.delete(address);
        }
        // @Transactional hará flush al salir del método.
    }
}
