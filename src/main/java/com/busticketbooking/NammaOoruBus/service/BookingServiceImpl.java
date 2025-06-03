package com.busticketbooking.NammaOoruBus.service;

import com.busticketbooking.NammaOoruBus.entity.Bookings;
import com.busticketbooking.NammaOoruBus.repository.BookingRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;

    @Autowired
    public BookingServiceImpl(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Override
    public List<Bookings> viewAllBus() {
        return bookingRepo.findAll();
    }

    @Override
    public List<Bookings> getBookingsByEmail(String email) {
        return bookingRepo.findByEmail(email);
    }

    @Override
    public Bookings saveBooking(Bookings booking) {
        return bookingRepo.save(booking);
    }

    @Override
    public void generatePdfWithPDFBox(Bookings booking, HttpServletResponse response) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Fonts using PDFBox 3.0.5 Standard14Fonts
            PDFont boldFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDFont regularFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(boldFont, 20);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Booking Details");
                contentStream.endText();

                contentStream.setFont(regularFont, 12);

                int y = 720;
                int lineHeight = 20;

                String[] details = {
                        "Name: " + booking.getName(),
                        "Email: " + booking.getEmail(),
                        "Phone Number: " + booking.getPhoneNumber(),
                        "Age: " + booking.getAge(),
                        "Gender: " + booking.getGender(),
                        "Bus Number: " + booking.getBusNumber(),
                        "Bus Name: " + booking.getBusName(),
                        "Source: " + booking.getSource(),
                        "Destination: " + booking.getDestination(),
                        "Price: " + booking.getPrice(),
                        "Date: " + booking.getDate(),
                        "Time: " + booking.getTime(),
                        "Duration: " + booking.getDuration()
                };

                for (String detail : details) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, y);
                    contentStream.showText(detail);
                    contentStream.endText();
                    y -= lineHeight;
                }
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=booking.pdf");
            document.save(response.getOutputStream());
            response.getOutputStream().flush();
        }
    }
}


