package com.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.booking.converter.PropertyConverter;
import com.booking.converter.RoomConverter;
import com.booking.dto.PropertyDto;
import com.booking.dto.RoomDto;
import com.booking.model.Property;
import com.booking.model.Room;
import com.booking.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

  private static final Integer PROPERTY_ID = 1;

  @Mock
  private PropertyRepository propertyRepository;

  @Spy
  private final PropertyConverter propertyConverter = new PropertyConverter(new RoomConverter());

  @InjectMocks
  private PropertyService propertyService;

  @Test
  void shouldCreateProperty() {
    // Given
    Property property = buildProperty();
    PropertyDto propertyDto = buildPropertyDto();
    when(propertyRepository.save(any())).thenReturn(property);

    // When
    PropertyDto savedProperty = propertyService.saveProperty(propertyDto);

    // Then
    assertNotNull(savedProperty.getId());
    assertEquals(1, savedProperty.getId());
  }

  @Test
  void shouldFindAllProperties() {
    // Given
    when(propertyRepository.findAll()).thenReturn(List.of(buildProperty()));

    // When
    List<PropertyDto> properties = propertyService.findAllProperties();

    // Then
    assertEquals(1, properties.size());
    for (PropertyDto property : properties) {
      assertNotNull(property.getId());
    }
  }

  @Test
  void shouldFindProperty() {
    // Given
    when(propertyRepository.findById(PROPERTY_ID)).thenReturn(Optional.of(buildProperty()));

    // When
    PropertyDto propertyDto = propertyService.findProperty(PROPERTY_ID);

    // Then
    assertNotNull(propertyDto);
    assertEquals(PROPERTY_ID, propertyDto.getId());
  }

  @Test
  void shouldNotFindProperty() {
    // Given
    when(propertyRepository.findById(PROPERTY_ID)).thenReturn(Optional.empty());

    // When
    Exception exception = assertThrows(EntityNotFoundException.class,
        () -> propertyService.findProperty(PROPERTY_ID));

    // Then
    assertEquals(String.format("Property with id: %s not found", PROPERTY_ID),
        exception.getMessage());
  }

  private PropertyDto buildPropertyDto() {
    return PropertyDto.builder()
        .name("Laguna Hotel")
        .address("Main Lane 22A")
        .contactPhone("89-45-34")
        .rooms(List.of(RoomDto.builder()
            .roomNumber(1)
            .price(100.00)
            .status("AVAILABLE")
            .build()))
        .build();
  }

  private Property buildProperty() {
    final Property property = Property.builder()
        .id(PROPERTY_ID)
        .name("Laguna Hotel")
        .address("Main Lane 22A")
        .contactPhone("89-45-34")
        .build();
    property.setRooms(List.of(Room.builder()
        .property(property)
        .roomNumber(1)
        .price(100.00)
        .status("AVAILABLE")
        .build()));
    return property;
  }

}
