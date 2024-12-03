package com.journey_back.repository;

import com.journey_back.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserRepository extends JpaRepository<UserModel, Integer> {
    boolean existsByEmail(String email);

    UserDetails findByEmail(String email);
}
