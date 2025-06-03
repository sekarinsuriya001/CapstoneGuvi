package com.busticketbooking.NammaOoruBus.controller;

import com.busticketbooking.NammaOoruBus.dto.UserDto;
import com.busticketbooking.NammaOoruBus.entity.Bookings;
import com.busticketbooking.NammaOoruBus.service.BookingService;
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
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final BookingService bookingService;
    private final UserService userService;

    /**
     * User dashboard page.
     * Make sure /templates/user.html exists.
     */
    @GetMapping
    public String showUser(Model model, @RequestParam(value = "success", required = false) String success) {
        // Optionally, pass a success message if redirected after update
        if (success != null) {
            model.addAttribute("successMessage", "Profile updated successfully!");
        }
        // Optionally, add user info to the model if needed for the dashboard
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDto userDto = userService.findUserByEmail(email);
        model.addAttribute("userDto", userDto);
        return "user"; // Ensure user.html exists in templates
    }

    /**
     * View all bookings for the logged-in user.
     */
    @GetMapping("/user-viewBookings")
    public String viewAllBookings(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Bookings> bookings = bookingService.getBookingsByEmail(email);
        if (bookings.isEmpty()) {
            model.addAttribute("errorMessage", "No Bookings found.");
        }
        model.addAttribute("bookings", bookings);
        return "user-viewBookings"; // Ensure user-user-admin-viewBookings.html exists
    }

    /**
     * Show form to update user details.
     */
    @GetMapping("/update")
    public String showUpdateDetailsFormAlias(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDto userDto = userService.findUserByEmail(email);
        model.addAttribute("userDto", userDto);
        return "user-update";
    }

    /**
     * Handle update of user details.
     */
    @PostMapping("/update-details/{id}")
    public String updateUserDetails(@PathVariable Long id,
                                    @Valid @ModelAttribute("userDto") UserDto userDto,
                                    BindingResult result,
                                    Model model) {
        if (result.hasErrors()) {
            return "user-update";
        }
        try {
            userService.updateUser(userDto, String.valueOf(id));
            // Redirect to dashboard with success message
            return "redirect:/user?success";
        } catch (EmailPresent ex) {
            model.addAttribute("errorMessage", "Email Already Present");
            return "user-update";
        } catch (PhoneNumberPresent ex) {
            model.addAttribute("errorMessage", "Phone Number Already Present");
            return "user-update";
        }
    }

    /**
     * Show password update page.
     */
    @GetMapping("/user-editPass")
    public String showPasswordUpdatePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDto userDto = userService.findUserByEmail(email);
        model.addAttribute("userDto", userDto);
        return "user-editPass"; // Ensure user-editPass.html exists
    }
}