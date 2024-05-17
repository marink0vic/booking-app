package com.booking.controller;

import com.booking.dto.BookingDto;
import com.booking.dto.CanceledBookingDto;
import com.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
@Slf4j
@Validated
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  public BookingDto createBooking(@Valid @RequestBody BookingDto bookingDto) {
    log.info("Creating new Booking for guest: {}", bookingDto.getGuestName());
    return bookingService.saveBooking(bookingDto);
  }

  @DeleteMapping(value = "/{id}")
  public CanceledBookingDto cancelBooking(@PathVariable("id") Integer bookingId) {
    return bookingService.cancelBooking(bookingId);
  }

}
