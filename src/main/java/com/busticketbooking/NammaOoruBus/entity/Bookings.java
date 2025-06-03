package com.busticketbooking.NammaOoruBus.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "booking")
public class Bookings {
    @Id
    private String id; // MongoDB uses String for ObjectId by default
    private String name;
    private String email;
    private String phoneNumber;
    private int age;
    private String gender;
    private String busNumber;
    private String busName;
    private String source;
    private String destination;
    private double price;
    private String date;
    private String time;
    private Long duration;
}