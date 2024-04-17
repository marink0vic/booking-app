package com.booking.controller;

import com.booking.dto.BookingDto;
import com.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
@Slf4j
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  public BookingDto createBooking(@Valid @RequestBody BookingDto bookingDto) {
    log.info("Creating new Booking for guest: {}", bookingDto.getGuestName());
    return bookingService.saveBooking(bookingDto);
  }

}
