package ru.moonlightmoth.keyzz_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moonlightmoth.keyzz_backend.model.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
