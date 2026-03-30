package com.edusmart.service;

import com.edusmart.model.Room;
import com.edusmart.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(String id, Room updatedRoom) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room.setName(updatedRoom.getName());
        room.setType(updatedRoom.getType());
        room.setCapacity(updatedRoom.getCapacity());
        room.setDepartment(updatedRoom.getDepartment());
        room.setAvailable(updatedRoom.isAvailable());
        return roomRepository.save(room);
    }

    public void deleteRoom(String id) {
        roomRepository.deleteById(id);
    }
}