package com.edusmart.repository;

import com.edusmart.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByType(String type);
    List<Room> findByAvailable(boolean available);
}