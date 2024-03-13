package com.apireserve.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apireserve.entities.User;
import com.apireserve.entities.enums.UserLevel;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
	Optional<User> findByEmailContainingIgnoreCase(@Param("email") String email);
	List<User> findAllByUserLevel(@Param("userLevel") UserLevel userLevel);
}
