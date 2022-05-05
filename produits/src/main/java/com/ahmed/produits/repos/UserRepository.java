package com.ahmed.produits.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ahmed.produits.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}