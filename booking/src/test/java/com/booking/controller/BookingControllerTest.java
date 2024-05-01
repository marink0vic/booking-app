package com.booking.controller;

import com.booking.dto.BookingDto;
import com.booking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    void shouldCreateBooking() throws Exception {
        BookingDto booking = buildBookingDto();
        when(bookingService.saveBooking(any(BookingDto.class))).thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createBookingRequest()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        verify(bookingService, times(1)).saveBooking(any(BookingDto.class));
    }

    private String createBookingRequest() {
        return """
                {
                  "roomId" : 601,
                  "checkInDate": "2024-07-23",
                  "checkOutDate": "2024-07-30",
                  "guestName": "John",
                  "guestContactInfo": "john@gmail.com",
                  "numberOfGuests": 2
                }
                """;
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

}
