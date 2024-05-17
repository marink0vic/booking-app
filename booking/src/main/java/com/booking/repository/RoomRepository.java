package com.booking.repository;

import com.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByIdAndStatus(Integer roomId, String status);
}
