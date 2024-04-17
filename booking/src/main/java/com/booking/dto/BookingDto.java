package com.booking.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {

  // TODO: add validation

  private Integer id;

  private Integer roomId;

  private LocalDate checkInDate;

  private LocalDate checkOutDate;

  private String guestName;

  private String guestContactInfo;

  private Integer numberOfGuests;

}
