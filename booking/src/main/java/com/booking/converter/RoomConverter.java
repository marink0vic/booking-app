package com.booking.converter;

import com.booking.dto.RoomDto;
import com.booking.model.Property;
import com.booking.model.Room;
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
    return rooms.stream()
        .map(this::toDto)
        .toList();
  }

  public List<Room> toEntityList(List<RoomDto> rooms, Property property) {
    return rooms.stream()
        .map(room -> toEntity(room, property))
        .toList();
  }

}
