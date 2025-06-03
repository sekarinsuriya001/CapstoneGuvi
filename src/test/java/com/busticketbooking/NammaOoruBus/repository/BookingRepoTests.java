package com.busticketbooking.NammaOoruBus.repository;

import com.busticketbooking.NammaOoruBus.entity.Bookings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class BookingRepoTests {

    @Autowired
    private BookingRepo bookingRepo;

    @Test
    @DisplayName("Test case for finding bookings by email")
    public void givenBookings_whenFindByEmail_thenReturnBookingList() {
        // given
        Bookings booking1 = new Bookings();
        booking1.setName("Alice");
        booking1.setEmail("alice@example.com");
        booking1.setPhoneNumber("1234567890");
        booking1.setAge(25);
        booking1.setGender("Female");
        booking1.setBusNumber("B123");
        booking1.setBusName("City Express");
        booking1.setSource("CityA");
        booking1.setDestination("CityB");
        booking1.setPrice(500.0);
        booking1.setDate("2024-12-03");
        booking1.setTime("10:00 AM");
        booking1.setDuration(4L);

        Bookings booking2 = new Bookings();
        booking2.setName("Bob");
        booking2.setEmail("alice@example.com");
        booking2.setPhoneNumber("9876543210");
        booking2.setAge(30);
        booking2.setGender("Male");
        booking2.setBusNumber("B124");
        booking2.setBusName("Fast Track");
        booking2.setSource("CityC");
        booking2.setDestination("CityD");
        booking2.setPrice(600.0);
        booking2.setDate("2024-12-04");
        booking2.setTime("3:00 PM");
        booking2.setDuration(4L);

        bookingRepo.save(booking1);
        bookingRepo.save(booking2);

        // when
        List<Bookings> bookingsList = bookingRepo.findByEmail("alice@example.com");

        // then
        assertThat(bookingsList).hasSize(2);
        assertThat(bookingsList)
                .extracting(Bookings::getName)
                .contains("Alice", "Bob");
    }
}