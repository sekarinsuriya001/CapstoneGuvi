package com.busticketbooking.NammaOoruBus.service;

import com.busticketbooking.NammaOoruBus.dto.BusDataDto;
import com.busticketbooking.NammaOoruBus.entity.BusData;
import com.busticketbooking.NammaOoruBus.repository.BusDataRepo;
import com.busticketbooking.NammaOoruBus.service.BusDataService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusDataServiceImpl implements BusDataService {

    @Autowired
    private BusDataRepo busDataRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BusDataDto addBus(BusDataDto busDataDto) {
        BusData busData = modelMapper.map(busDataDto, BusData.class);
        BusData saved = busDataRepo.save(busData);
        return modelMapper.map(saved, BusDataDto.class);
    }

    @Override
    public BusDataDto updateBus(String id, BusDataDto busDataDto) {
        BusData busData = busDataRepo.findById(id).orElseThrow();
        modelMapper.map(busDataDto, busData);
        BusData updated = busDataRepo.save(busData);
        return modelMapper.map(updated, BusDataDto.class);
    }

    @Override
    public void deleteBus(String id) {
        busDataRepo.deleteById(id);
    }

    @Override
    public BusDataDto viewBus(String id) {
        BusData busData = busDataRepo.findById(id).orElseThrow();
        return modelMapper.map(busData, BusDataDto.class);
    }

    @Override
    public List<BusDataDto> searchBuses(String source, String destination, String date) {
        List<BusData> buses = busDataRepo.findBySourceAndDestinationAndDate(source, destination, date);
        return buses.stream().map(bus -> modelMapper.map(bus, BusDataDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<BusDataDto> getAllBuses() {
        return busDataRepo.findAll().stream()
                .map(bus -> modelMapper.map(bus, BusDataDto.class))
                .collect(Collectors.toList());
    }
}