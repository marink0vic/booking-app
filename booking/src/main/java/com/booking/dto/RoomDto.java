package com.booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @NotNull
  private Integer roomNumber;

  @NotNull
  @Min(value = 1, message = "Value must be greater than 1")
  private Double price;

  @NotBlank
  private String status;

}
