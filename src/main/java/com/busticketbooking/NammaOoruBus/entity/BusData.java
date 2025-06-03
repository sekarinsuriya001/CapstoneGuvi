package com.busticketbooking.NammaOoruBus.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "busdata")
public class BusData {
    @Id
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