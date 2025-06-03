package com.busticketbooking.NammaOoruBus.controller;

import com.busticketbooking.NammaOoruBus.dto.BusDataDto;
import com.busticketbooking.NammaOoruBus.dto.UserDto;
import com.busticketbooking.NammaOoruBus.repository.BusDataRepo;
import com.busticketbooking.NammaOoruBus.service.BusDataService;
import com.busticketbooking.NammaOoruBus.service.UserService;
import com.busticketbooking.NammaOoruBus.exception.EmailPresent;
import com.busticketbooking.NammaOoruBus.exception.PhoneNumberPresent;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UserService userService;
    private final BusDataService busDataService;
    private final BusDataRepo busDataRepo;

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login"; // Redirect to the login page
    }

    @GetMapping("/home")
    public String showhome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDto user = userService.findUserByEmail(email);
        model.addAttribute("user", user);
        return "home";  // Return the home page view
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String registerUser(@ModelAttribute @Valid UserDto userDto, BindingResult result, Model model)  {
        if (result.hasErrors()) {
            return "register"; // Return to the form page if validation errors exist
        }
        try {
            userService.addUser(userDto);
            return "redirect:/register?success";
        } catch (EmailPresent ex) {
            model.addAttribute("errorMessage", "Email Already Present");
            return "register";
        } catch (PhoneNumberPresent ex) {
            model.addAttribute("errorMessage", "Phone Number Already Present");
            return "register";
        }
    }

    @GetMapping("/routes")
    public String searchBuses(@RequestParam String from,
                              @RequestParam String to,
                              @RequestParam String date,
                              Model model) {
        // Search for buses based on the input parameters
        List<BusDataDto> buses = busDataService.searchBuses(from, to, date);
        if (buses.isEmpty()) {
            model.addAttribute("errorMessage", "No buses found for the selected route and date.");
        }
        model.addAttribute("buses", buses);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("date", date);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDto user = userService.findUserByEmail(email);
        model.addAttribute("user", user);
        return "home";  // Return to the same page with search results
    }
}