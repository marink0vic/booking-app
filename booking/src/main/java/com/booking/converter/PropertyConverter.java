package com.booking.converter;

import com.booking.dto.PropertyDto;
import com.booking.model.Property;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyConverter {

  private final RoomConverter roomConverter;

  public PropertyDto toDto(Property property) {
    return PropertyDto.builder()
        .id(property.getId())
        .name(property.getName())
        .address(property.getAddress())
        .contactPhone(property.getContactPhone())
        .rooms(roomConverter.toDtoList(property.getRooms()))
        .build();
  }

  public Property toEntity(PropertyDto propertyDto) {
    Property property = Property.builder()
        .name(propertyDto.getName())
        .address(propertyDto.getAddress())
        .contactPhone(propertyDto.getContactPhone())
        .build();
    property.setRooms(roomConverter.toEntityList(propertyDto.getRooms(), property));
    return property;
  }

  public List<PropertyDto> toDtoList(List<Property> properties) {
    return properties.stream()
        .map(this::toDto)
        .toList();
  }

}
