package com.busticketbooking.NammaOoruBus.service;

import com.busticketbooking.NammaOoruBus.entity.Bookings;
import com.busticketbooking.NammaOoruBus.repository.BookingRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTests {

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Bookings booking;

    @BeforeEach
    void setUp() {
        booking = new Bookings();
        booking.setId("1"); // Assuming MongoDB uses String id
        booking.setName("John Doe");
        booking.setEmail("john@example.com");
        booking.setAge(30);
        booking.setGender("Male");
        booking.setPhoneNumber("1234567890");
        booking.setBusName("Test Bus");
        booking.setSource("City A");
        booking.setDestination("City B");
        booking.setDate("2024-12-01");
        booking.setTime("10:00 AM");
        booking.setPrice(500.0);
    }

    @Test
    void givenBooking_whenSaveBooking_thenReturnSavedBooking() {
        // given
        given(bookingRepo.save(booking)).willReturn(booking);

        // when
        Bookings savedBooking = bookingService.saveBooking(booking);

        // then
        assertThat(savedBooking).isNotNull();
        assertThat(savedBooking.getId()).isEqualTo("1");
        assertThat(savedBooking.getName()).isEqualTo("John Doe");
        verify(bookingRepo, times(1)).save(booking);
    }

    @Test
    void givenEmail_whenGetBookingsByEmail_thenReturnBookingList() {
        // given
        List<Bookings> bookings = Arrays.asList(booking);
        given(bookingRepo.findByEmail("john@example.com")).willReturn(bookings);

        // when
        List<Bookings> fetchedBookings = bookingService.getBookingsByEmail("john@example.com");

        // then
        assertThat(fetchedBookings).isNotNull();
        assertThat(fetchedBookings.size()).isEqualTo(1);
        verify(bookingRepo, times(1)).findByEmail("john@example.com");
    }
}