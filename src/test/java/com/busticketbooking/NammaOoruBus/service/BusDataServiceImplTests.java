package com.busticketbooking.NammaOoruBus.service;

import com.busticketbooking.NammaOoruBus.dto.BusDataDto;
import com.busticketbooking.NammaOoruBus.entity.BusData;
import com.busticketbooking.NammaOoruBus.repository.BusDataRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusDataServiceImplTests {

    @Mock
    private BusDataRepo busDataRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BusDataServiceImpl busDataService;

    private BusDataDto busDataDto;
    private BusData busData;

    @BeforeEach
    void setUp() {
        busDataDto = new BusDataDto();
        busDataDto.setBusNumber("BUS123");
        busDataDto.setBusName("Test Bus");
        busDataDto.setCapacity(40L);
        busDataDto.setSource("City A");
        busDataDto.setDestination("City B");
        busDataDto.setPrice(500L);
        busDataDto.setDate("2024-12-01");
        busDataDto.setTime("10:00 AM");
        busDataDto.setDuration(5L);
        busDataDto.setAvailableSeats(30L);

        busData = new BusData();
        busData.setId("1");
        busData.setBusNumber("BUS123");
        busData.setBusName("Test Bus");
        busData.setCapacity(40L);
        busData.setSource("City A");
        busData.setDestination("City B");
        busData.setPrice(500L);
        busData.setDate("2024-12-01");
        busData.setTime("10:00 AM");
        busData.setDuration(5L);
        busData.setAvailableSeats(30L);
    }

    @Test
    void givenBusDataDto_whenAddBus_thenReturnSavedBusDataDto() {
        given(modelMapper.map(busDataDto, BusData.class)).willReturn(busData);
        given(busDataRepo.save(busData)).willReturn(busData);
        given(modelMapper.map(busData, BusDataDto.class)).willReturn(busDataDto);

        BusDataDto savedBus = busDataService.addBus(busDataDto);

        assertThat(savedBus).isNotNull();
        assertThat(savedBus.getBusNumber()).isEqualTo(busDataDto.getBusNumber());
        verify(busDataRepo, times(1)).save(busData);
    }

    @Test
    void givenBusDataDtoAndId_whenUpdateBus_thenReturnUpdatedBusDataDto() {
        given(busDataRepo.findById(busData.getId())).willReturn(Optional.of(busData));
        // For modelMapper.map(busDataDto, busData)
        doAnswer(invocation -> {
            BusDataDto source = invocation.getArgument(0);
            BusData destination = invocation.getArgument(1);
            destination.setBusNumber(source.getBusNumber());
            destination.setBusName(source.getBusName());
            destination.setCapacity(source.getCapacity());
            destination.setSource(source.getSource());
            destination.setDestination(source.getDestination());
            destination.setPrice(source.getPrice());
            destination.setDate(source.getDate());
            destination.setTime(source.getTime());
            destination.setDuration(source.getDuration());
            destination.setAvailableSeats(source.getAvailableSeats());
            return null;
        }).when(modelMapper).map(eq(busDataDto), eq(busData));
        given(busDataRepo.save(busData)).willReturn(busData);
        given(modelMapper.map(busData, BusDataDto.class)).willReturn(busDataDto);

        BusDataDto updatedBus = busDataService.updateBus(busData.getId(), busDataDto);

        assertThat(updatedBus).isNotNull();
        assertThat(updatedBus.getBusName()).isEqualTo(busDataDto.getBusName());
        verify(busDataRepo, times(1)).save(busData);
    }

    @Test
    void givenBusId_whenDeleteBus_thenVerifyDeletion() {
        String busId = "1";
        busDataService.deleteBus(busId);
        verify(busDataRepo, times(1)).deleteById(busId);
    }

    @Test
    void givenBusId_whenViewBus_thenReturnBusDataDto() {
        given(busDataRepo.findById(busData.getId())).willReturn(Optional.of(busData));
        given(modelMapper.map(busData, BusDataDto.class)).willReturn(busDataDto);

        BusDataDto fetchedBus = busDataService.viewBus(busData.getId());

        assertThat(fetchedBus).isNotNull();
        assertThat(fetchedBus.getBusNumber()).isEqualTo(busDataDto.getBusNumber());
    }

    @Test
    void whenViewAllBuses_thenReturnListOfBusDataDtos() {
        List<BusData> buses = List.of(busData);
        given(busDataRepo.findAll()).willReturn(buses);
        given(modelMapper.map(busData, BusDataDto.class)).willReturn(busDataDto);

        List<BusDataDto> fetchedBuses = busDataService.getAllBuses();

        assertThat(fetchedBuses).isNotNull();
        assertThat(fetchedBuses).hasSize(1);
        verify(busDataRepo, times(1)).findAll();
    }

    @Test
    void givenSourceDestinationAndDate_whenSearchBuses_thenReturnListOfBusDataDtos() {
        List<BusData> buses = List.of(busData);
        given(busDataRepo.findBySourceAndDestinationAndDate("City A", "City B", "2024-12-01")).willReturn(buses);
        given(modelMapper.map(busData, BusDataDto.class)).willReturn(busDataDto);

        List<BusDataDto> searchedBuses = busDataService.searchBuses("City A", "City B", "2024-12-01");

        assertThat(searchedBuses).isNotNull();
        assertThat(searchedBuses).hasSize(1);
        verify(busDataRepo, times(1)).findBySourceAndDestinationAndDate("City A", "City B", "2024-12-01");
    }
}