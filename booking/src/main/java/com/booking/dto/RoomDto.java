package com.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto {

  private Integer id;

  private Integer propertyId;

  private Integer roomNumber;

  private Double price;

  private String status;

}