package com.omega.server.repository;

import com.omega.server.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

   List<Address> findAllByIsDeletedFalse();
}
