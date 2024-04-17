package com.booking.converter;

import com.booking.dto.BookingDto;
import com.booking.model.Booking;
import com.booking.model.Room;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookingConverter {

  public BookingDto toDto(Booking booking) {
    return BookingDto.builder()
        .id(booking.getId())
        .roomId(booking.getRoom().getId())
        .checkInDate(booking.getCheckInDate())
        .checkOutDate(booking.getCheckOutDate())
        .guestName(booking.getGuestName())
        .guestContactInfo(booking.getGuestContactInfo())
        .numberOfGuests(booking.getNumberOfGuests())
        .build();
  }

  public Booking toEntity(BookingDto bookingDto, Room room) {
    return Booking.builder()
        .room(room)
        .checkInDate(bookingDto.getCheckInDate())
        .checkOutDate(bookingDto.getCheckOutDate())
        .guestName(bookingDto.getGuestName())
        .guestContactInfo(bookingDto.getGuestContactInfo())
        .numberOfGuests(bookingDto.getNumberOfGuests())
        .build();
  }

  public List<BookingDto> toDtoList(List<Booking> bookings) {
    List<BookingDto> bookingDtos = new ArrayList<>();
    for (Booking booking: bookings) {
      bookingDtos.add(toDto(booking));
    }
    return bookingDtos;
  }

  public List<Booking> toEntityList(List<BookingDto> bookingDtos, Room room) {
    List<Booking> bookings = new ArrayList<>();
    for (BookingDto bookingDto: bookingDtos) {
      bookings.add(toEntity(bookingDto, room));
    }
    return bookings;
  }

}
