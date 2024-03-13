package com.apireserve.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apireserve.entities.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, UUID>{

}
