package com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.controller;

import com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.dto.BookingDTO;
import com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.service.BookingService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/api/bookings")
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{externalId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found"),
            @ApiResponse(responseCode = "404", description = "No booking found")
    })
    public ResponseEntity<BookingDTO> getBookingByExternalId(@PathVariable UUID externalId) {
        BookingDTO bookingDTO = bookingService.getBookingById(externalId);

        if (bookingDTO != null) {
            return ResponseEntity.ok(bookingDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDTO createBooking(@RequestBody BookingDTO booking) {
        if (booking.getExternalId() != null) {
            // Wenn externalId mitgegeben wird, wirf eine Exception
            throw new IllegalArgumentException("ExternalId should not be provided in the request body for new bookings.");
        }
        return bookingService.createBooking(booking);
    }

    @PutMapping("/{externalId}")
    public BookingDTO updateBooking(@PathVariable UUID externalId, @RequestBody BookingDTO newBooking) {
        return bookingService.updateBooking(externalId, newBooking);
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Object> deleteBooking(@PathVariable UUID externalId) {
        bookingService.deleteBooking(externalId);
        return ResponseEntity.noContent().build();
    }
}