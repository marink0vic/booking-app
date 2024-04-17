package com.booking.converter;

import com.booking.dto.RoomDto;
import com.booking.model.Property;
import com.booking.model.Room;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RoomConverter {

  public RoomDto toDto(Room room) {
    return RoomDto.builder()
        .id(room.getId())
        .propertyId(room.getProperty().getId())
        .roomNumber(room.getRoomNumber())
        .price(room.getPrice())
        .status(room.getStatus())
        .build();
  }

  public Room toEntity(RoomDto room, Property property) {
    return Room.builder()
        .roomNumber(room.getRoomNumber())
        .price(room.getPrice())
        .status(room.getStatus())
        .property(property)
        .build();
  }

  public List<RoomDto> toDtoList(List<Room> rooms) {
    List<RoomDto> roomDtos = new ArrayList<>();
    for (Room room: rooms) {
      roomDtos.add(toDto(room));
    }
    return roomDtos;
  }

  public List<Room> toEntityList(List<RoomDto> roomDtos, Property property) {
    List<Room> rooms = new ArrayList<>();
    for (RoomDto roomDto: roomDtos) {
      rooms.add(toEntity(roomDto, property));
    }
    return rooms;
  }

}
