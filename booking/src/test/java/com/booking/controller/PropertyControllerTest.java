package com.booking.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.booking.dto.PropertyDto;
import com.booking.dto.RoomDto;
import com.booking.service.PropertyService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(PropertyController.class)
class PropertyControllerTest {

  private static final Integer PROPERTY_ID = 1;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PropertyService propertyService;

  @Test
  void shouldCreateProperty() throws Exception {
    PropertyDto property = buildPropertyDto();
    when(propertyService.saveProperty(any(PropertyDto.class))).thenReturn(property);

    mockMvc.perform(MockMvcRequestBuilders.post("/properties")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createPropertyRequest()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

    verify(propertyService, times(1)).saveProperty(any(PropertyDto.class));
  }

  @Test
  void shouldFindAllProperties() throws Exception {
    PropertyDto property = buildPropertyDto();
    when(propertyService.findAllProperties()).thenReturn(List.of(property));

    mockMvc.perform(MockMvcRequestBuilders.get("/properties"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists());

    verify(propertyService, times(1)).findAllProperties();
  }

  @Test
  void shouldFindProperty() throws Exception {
    PropertyDto property = buildPropertyDto();
    when(propertyService.findProperty(PROPERTY_ID)).thenReturn(property);

    mockMvc.perform(MockMvcRequestBuilders.get(String.format("/properties/%s", PROPERTY_ID)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

    verify(propertyService, times(1)).findProperty(PROPERTY_ID);
  }

  @Test
  void shouldNotFindProperty() throws Exception {
    when(propertyService.findProperty(PROPERTY_ID)).thenThrow(new EntityNotFoundException());

    mockMvc.perform(MockMvcRequestBuilders.get("/properties/" + PROPERTY_ID))
        .andExpect(MockMvcResultMatchers.status().isNotFound());

    verify(propertyService, times(1)).findProperty(PROPERTY_ID);
  }

  private String createPropertyRequest() {
    return """
        {
            "name": "Blue Horizon Apartments",
            "address": "Sunset Boulevard 34",
            "contactPhone": "345-433-023",
            "rooms" : [
                {
                    "roomNumber": 601,
                    "price": 100.00,
                    "status": "AVAILABLE"
                }
            ]
        }
        """;
  }

  private PropertyDto buildPropertyDto() {
    return PropertyDto.builder()
        .id(PROPERTY_ID)
        .name("Blue Horizon Apartments")
        .address("Sunset Boulevard 34")
        .contactPhone("345-433-023")
        .rooms(List.of(RoomDto.builder()
            .roomNumber(601)
            .price(100.00)
            .status("AVAILABLE")
            .build()))
        .build();
  }

}
