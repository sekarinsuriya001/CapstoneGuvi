package com.busticketbooking.NammaOoruBus.controller;

import com.busticketbooking.NammaOoruBus.dto.BookDto;
import com.busticketbooking.NammaOoruBus.dto.BusDataDto;
import com.busticketbooking.NammaOoruBus.entity.Bookings;
import com.busticketbooking.NammaOoruBus.service.BookingServiceImpl;
import com.busticketbooking.NammaOoruBus.service.BusDataService;
import com.busticketbooking.NammaOoruBus.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@AllArgsConstructor
@Controller
public class BookingController {

    private final BookingServiceImpl bookingsService;
    private final BusDataService busDataService;
    private final UserService userService;

    @GetMapping("/bookings/new")
    public String showBookingForm(@RequestParam ("busId1") String busId, Model model) {
        BusDataDto bus = busDataService.viewBus(busId);
        model.addAttribute("bus", bus);
        return "booking-form";
    }

    @PostMapping("/bookings/confirm")
    public void confirmBooking(@RequestParam String busId, @ModelAttribute BookDto bookDto, HttpServletResponse response) throws IOException {
        // Retrieve bus details
        BusDataDto bus = busDataService.viewBus(busId);

        // Create and populate booking entity
        Bookings bookings = new Bookings();
        bookings.setBusName(bus.getBusName());
        bookings.setBusNumber(bus.getBusNumber());
        bookings.setDate(bus.getDate());
        bookings.setDestination(bus.getDestination());
        bookings.setTime(bus.getTime());
        bookings.setDuration(bus.getDuration());
        bookings.setPrice(bus.getPrice());
        bookings.setSource(bus.getSource());

        bookings.setName(bookDto.getName());
        bookings.setEmail(bookDto.getEmail());
        bookings.setPhoneNumber(bookDto.getPhoneNumber());
        bookings.setAge(bookDto.getAge());
        bookings.setGender(bookDto.getGender());

        // Save booking
        bookingsService.saveBooking(bookings);

        // Generate and send the PDF
        bookingsService.generatePdfWithPDFBox(bookings, response);
        // Do not return a view or redirect, as the response is already committed
    }
}