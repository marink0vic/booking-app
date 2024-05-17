package com.booking.service;

import com.booking.converter.BookingConverter;
import com.booking.dto.BookingDto;
import com.booking.dto.CanceledBookingDto;
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
    
    private static final String ROOM_AVAILABLE = "AVAILABLE";
    private static final String ROOM_BOOKED = "BOOKED";
    private static final String BOOKING_ACTIVE = "ACTIVE";
    private static final String BOOKING_CANCELED = "CANCELED";

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingConverter bookingConverter;

    @Transactional
    public BookingDto saveBooking(BookingDto bookingDto) {
        Room room = roomRepository.findByIdAndStatus(bookingDto.getRoomId(), ROOM_AVAILABLE).orElseThrow(
            () -> new EntityNotFoundException(
                String.format("Available room with id: %s not found", bookingDto.getRoomId())));
        final Booking booking = bookingConverter.toEntity(bookingDto, room);
        booking.setStatus(BOOKING_ACTIVE);
        Booking savedBooking = bookingRepository.save(booking);
        room.setStatus(ROOM_BOOKED);
        roomRepository.save(room);
        log.info("Room with id: {} booked", savedBooking.getRoom().getId());
        return bookingConverter.toDto(savedBooking);
    }

    @Transactional
    public CanceledBookingDto cancelBooking(Integer bookingId) {
        Booking booking = bookingRepository.findByIdAndStatus(bookingId, BOOKING_ACTIVE).orElseThrow(
                () -> new EntityNotFoundException(String.format("Active booking with id: %s not found", bookingId)));
        Room room = roomRepository.findByIdAndStatus(booking.getRoom().getId(), ROOM_BOOKED).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Booked room with id: %s not found", booking.getRoom().getId())));
        booking.setStatus(BOOKING_CANCELED);
        bookingRepository.save(booking);
        room.setStatus(ROOM_AVAILABLE);
        roomRepository.save(room);
        log.info("Booking with id: {} canceled", booking.getId());
        return bookingConverter.toCanceledBookingDto(booking);
    }
    
}
