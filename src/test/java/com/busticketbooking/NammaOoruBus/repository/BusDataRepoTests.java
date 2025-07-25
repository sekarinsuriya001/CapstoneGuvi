package com.busticketbooking.NammaOoruBus.repository;

import com.busticketbooking.NammaOoruBus.entity.BusData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test cases for BusDataRepo")
@DataMongoTest
public class BusDataRepoTests {

    @Autowired
    private BusDataRepo busDataRepo;

    @Test
    @DisplayName("Test case for finding buses by source, destination, and date")
    public void givenBusData_whenFindBySourceAndDestinationAndDate_thenReturnBuses() {
        busDataRepo.deleteAll(); // Clear collection before test

        // given
        BusData bus1 = new BusData();
        bus1.setBusNumber("ABC123");
        bus1.setBusName("Express Line");
        bus1.setCapacity(20L);
        bus1.setSource("CityA");
        bus1.setDestination("CityB");
        bus1.setDate("2024-12-02");
        bus1.setTime("10:00 AM");
        bus1.setDuration(4L);
        bus1.setPrice(500L);
        bus1.setAvailableSeats(20L);

        BusData bus2 = new BusData();
        bus2.setBusNumber("DEF456");
        bus2.setBusName("Speedster");
        bus2.setCapacity(20L);
        bus2.setSource("CityA");
        bus2.setDestination("CityB");
        bus2.setDate("2024-12-02");
        bus2.setTime("2:00 PM");
        bus2.setDuration(3L);
        bus2.setPrice(600L);
        bus2.setAvailableSeats(20L);

        busDataRepo.save(bus1);
        busDataRepo.save(bus2);

        // when
        List<BusData> buses = busDataRepo.findBySourceAndDestinationAndDate("CityA", "CityB", "2024-12-02");

        // then
        assertThat(buses).hasSize(2);
        assertThat(buses).extracting(BusData::getBusNumber).contains("ABC123", "DEF456");
    }

    @Test
    @DisplayName("Test case for checking if a bus exists by bus number")
    public void givenBusNumber_whenExistsByBusNumber_thenReturnTrue() {
        busDataRepo.deleteAll(); // Clear collection before test

        // given
        BusData bus = new BusData();
        bus.setBusNumber("XYZ789");
        bus.setBusName("Night Rider");
        bus.setSource("CityX");
        bus.setCapacity(20L);
        bus.setDestination("CityY");
        bus.setDate("2024-12-03");
        bus.setTime("11:00 PM");
        bus.setDuration(8L);
        bus.setPrice(800L);
        bus.setAvailableSeats(20L);

        busDataRepo.save(bus);

        // when
        boolean exists = busDataRepo.existsByBusNumber("XYZ789");

        // then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Test case for non-existing bus number")
    public void givenBusNumber_whenExistsByBusNumber_thenReturnFalse() {
        busDataRepo.deleteAll(); // Clear collection before test

        // when
        boolean exists = busDataRepo.existsByBusNumber("NON123");

        // then
        assertThat(exists).isFalse();
    }
}