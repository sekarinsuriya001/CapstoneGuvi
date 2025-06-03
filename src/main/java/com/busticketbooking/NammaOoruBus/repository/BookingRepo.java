package com.busticketbooking.NammaOoruBus.repository;

import com.busticketbooking.NammaOoruBus.entity.Bookings;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface BookingRepo extends MongoRepository<Bookings, String> {
    List<Bookings> findByEmail(String email);
}