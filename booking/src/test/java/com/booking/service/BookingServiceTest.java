package com.booking.service;

import com.booking.converter.BookingConverter;
import com.booking.dto.BookingDto;
import com.booking.model.Booking;
import com.booking.model.Room;
import com.booking.repository.BookingRepository;
import com.booking.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomRepository roomRepository;

    @Spy
    private BookingConverter bookingConverter;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void shouldSaveBooking() {
        // Given
        Booking booking = buildBooking();
        BookingDto bookingDto = buildBookingDto();
        when(roomRepository.findById(bookingDto.getRoomId())).thenReturn(Optional.ofNullable(buildRoom()));
        when(bookingRepository.save(any())).thenReturn(booking);

        // When
        BookingDto savedBooking = bookingService.saveBooking(bookingDto);

        // Then
        assertNotNull(savedBooking.getId());
        assertEquals(1, savedBooking.getId());
    }

    private Booking buildBooking() {
        return Booking.builder()
            .id(1)
            .room(buildRoom())
            .checkInDate(LocalDate.of(2024, 7, 23))
            .checkOutDate(LocalDate.of(2024, 7, 30))
            .guestName("John Doe")
            .guestContactInfo("john@gmail.com")
            .numberOfGuests(2)
            .build();
    }

    private BookingDto buildBookingDto() {
        return BookingDto.builder()
            .id(1)
            .roomId(601)
            .checkInDate(LocalDate.of(2024, 7, 23))
            .checkOutDate(LocalDate.of(2024, 7, 30))
            .guestName("John Doe")
            .guestContactInfo("john@gmail.com")
            .numberOfGuests(2)
            .build();
    }

    private Room buildRoom() {
        return Room.builder()
            .id(1)
            .build();
    }
}
