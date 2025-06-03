package com.busticketbooking.NammaOoruBus.service;

import com.busticketbooking.NammaOoruBus.dto.BusDataDto;
import java.util.List;

public interface BusDataService {
    BusDataDto addBus(BusDataDto busDataDto);
    BusDataDto updateBus(String id, BusDataDto busDataDto);
    void deleteBus(String id);
    BusDataDto viewBus(String id);
    List<BusDataDto> searchBuses(String source, String destination, String date);
    List<BusDataDto> getAllBuses();
}