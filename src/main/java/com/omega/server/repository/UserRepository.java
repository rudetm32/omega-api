package com.omega.server.repository;


import com.omega.server.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    @Query("SELECT u FROM User u WHERE u.isDeleted = false")
    Page<User> findAllActiveUsers(Pageable pageable);

    UserDetails findByUsername(String userName);
}
