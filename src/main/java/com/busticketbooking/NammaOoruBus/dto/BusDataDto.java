package com.busticketbooking.NammaOoruBus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDataDto {
    private String id;
    private String busNumber;
    private String busName;
    private Long capacity;
    private String source;
    private String destination;
    private Long price;
    private String date;
    private String time;
    private Long duration;
    private Long availableSeats;
}