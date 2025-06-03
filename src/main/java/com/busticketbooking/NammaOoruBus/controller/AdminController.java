package com.busticketbooking.NammaOoruBus.controller;

import com.busticketbooking.NammaOoruBus.dto.BusDataDto;
import com.busticketbooking.NammaOoruBus.dto.UserDto;
import com.busticketbooking.NammaOoruBus.entity.Bookings;
import com.busticketbooking.NammaOoruBus.repository.BusDataRepo;
import com.busticketbooking.NammaOoruBus.service.BookingService;
import com.busticketbooking.NammaOoruBus.service.BusDataService;
import com.busticketbooking.NammaOoruBus.service.UserService;
import com.busticketbooking.NammaOoruBus.exception.DuplicateBusNumberException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final BusDataService busDataService;
    private final UserService userService;
    private final BookingService bookingService;
    private final BusDataRepo busDataRepo;

    @GetMapping
    public String showAdmin() {
        return "admin";
    }

    @GetMapping("/admin-addBus")
    public String showBusRegistrationForm(Model model) {
        BusDataDto busDataDto = new BusDataDto();
        model.addAttribute("busDataDto", busDataDto);
        return "admin-addBus";
    }

    @PostMapping("/admin-addBus/save")
    public String registerBus(@ModelAttribute BusDataDto busDataDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin-addBus";
        }
        try {
            busDataService.addBus(busDataDto);
            return "redirect:/admin?success";
        } catch (DuplicateBusNumberException ex) {
            model.addAttribute("errorMessage", "BUS NUMBER ALREADY PRESENT");
            return "admin-addBus";
        }
    }

    @GetMapping("/admin-viewBuses")
    public String viewAllBus(Model model) {
        List<BusDataDto> buses = busDataService.getAllBuses();
        model.addAttribute("buses", buses);
        return "admin-viewBuses";
    }

    @GetMapping("/admin-viewUsers")
    public String users(Model model)  {
        List<UserDto> users = userService.viewAllUsers();
        model.addAttribute("users", users);
        return "admin-viewUsers";
    }

    @GetMapping("/admin-deleteBus")
    public String showDeleteBusPage(Model model) {
        List<BusDataDto> buses = busDataService.getAllBuses();
        model.addAttribute("buses", buses);
        return "admin-deleteBus";
    }

    // Delete bus
    @GetMapping("/admin-deleteBus/{id}")
    public String deleteBus(@PathVariable String id) {
        busDataService.deleteBus(id);
        return "redirect:/admin?success";
    }

    @GetMapping("/admin-updateBus")
    public String showUpdateBusPage(Model model){
        // Fetch all buses to display on the update page
        List<BusDataDto> buses = busDataService.getAllBuses();
        model.addAttribute("buses", buses);
        return "admin-updateBus";
    }

    @GetMapping("/admin-updateBus/{id}")
    public String showEditBusForm(@PathVariable ("id") String id, Model model) {
        BusDataDto bus = busDataService.viewBus(id);
        System.out.println(id);
        System.out.println(bus);
        model.addAttribute("bus", bus);
        return "admin-updateform"; // This page will be for editing a bus
    }

    @PostMapping("/admin-updateform/{id}")
    public String updateBus(@PathVariable String id, @ModelAttribute BusDataDto busDataDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin-updateform";
        }
        try {
            busDataService.updateBus(id, busDataDto);
            return "redirect:/admin?success";
        } catch (DuplicateBusNumberException ex) {
            model.addAttribute("errorMessage", "BUS NUMBER ALREADY PRESENT");
            return "admin-updateform";
        }
    }

    @GetMapping("/admin-viewBookings")
    public String viewAllBookings(Model model) {
        List<Bookings> bookings = bookingService.viewAllBus();
        model.addAttribute("bookings", bookings);
        return "admin-viewBookings";
    }
}