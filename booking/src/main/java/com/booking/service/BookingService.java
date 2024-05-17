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
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class BookingService {

  private final BookingRepository bookingRepository;
  private final RoomRepository roomRepository;
  private final BookingConverter bookingConverter;

  @Transactional
  public BookingDto saveBooking(BookingDto bookingDto) {
    Room room = roomRepository.findByIdAndStatus(bookingDto.getRoomId(), "AVAILABLE").orElseThrow(
        () -> new EntityNotFoundException(
            String.format("Available room with id: %s not found", bookingDto.getRoomId())));
     Booking booking = bookingRepository.save(bookingConverter.toEntity(bookingDto, room));
     room.setStatus("BOOKED");
     roomRepository.save(room);
     log.info("Room with id: {} booked", booking.getRoom().getId());

     return bookingConverter.toDto(booking);
  }
}
