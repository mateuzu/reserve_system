package com.apireserve.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apireserve.entities.Room;
import com.apireserve.entities.enums.Status;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID>{

	List<Room> findAllByStatus(@Param("status") Status status);
}
