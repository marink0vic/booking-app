package com.booking.service;

import com.booking.converter.BookingConverter;
import com.booking.dto.BookingDto;
import com.booking.model.Booking;
import com.booking.model.Room;
import com.booking.repository.BookingRepository;
import com.booking.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BookingService {

  private final BookingRepository bookingRepository;
  private final RoomRepository roomRepository;
  private final BookingConverter bookingConverter;

  public BookingDto saveBooking(BookingDto bookingDto) {
    /*
    * TODO: possible improvements
    *   1: find room by ID and status = AVAILABLE else NotFoundException
    *   2: after successful save for booking, change the status of room to BOOKED and update room in DB
    * */
    Room room = roomRepository.findById(bookingDto.getRoomId()).orElseThrow(
        () -> new EntityNotFoundException(
            String.format("Room with id: %s not found", bookingDto.getRoomId())));
     Booking booking = bookingRepository.save(bookingConverter.toEntity(bookingDto, room));
     log.info("Room with id: {} booked", booking.getRoom().getId());
     return bookingConverter.toDto(booking);
  }
}
