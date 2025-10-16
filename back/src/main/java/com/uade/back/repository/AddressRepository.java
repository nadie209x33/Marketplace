package com.uade.back.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.back.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    // Elimina la relación en la tabla intermedia
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM user_address WHERE uinfo_id = :uid AND address_id = :aid", nativeQuery = true)
    void unlinkUserAddress(@Param("uid") Integer userInfoId, @Param("aid") Integer addressId);

    // Cuenta cuántos usuarios siguen vinculados a la address
    @Query(value = "SELECT COUNT(*) FROM user_address WHERE address_id = :aid", nativeQuery = true)
    long countLinks(@Param("aid") Integer addressId);
}
