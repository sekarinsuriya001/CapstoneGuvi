package com.busticketbooking.NammaOoruBus.repository;

import com.busticketbooking.NammaOoruBus.entity.BusData;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface BusDataRepo extends MongoRepository<BusData, String> {
    List<BusData> findBySourceAndDestinationAndDate(String source, String destination, String date);
    boolean existsByBusNumber(String busNumber);
}