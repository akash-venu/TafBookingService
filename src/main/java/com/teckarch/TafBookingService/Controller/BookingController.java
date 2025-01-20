package com.teckarch.TafBookingService.Controller;

import com.teckarch.TafBookingService.DTO.BookingsDTO;
import com.teckarch.TafBookingService.Service.Interfaces.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create a new booking
    @PostMapping
    public ResponseEntity<BookingsDTO> createBooking(@RequestBody BookingsDTO booking) {
        BookingsDTO createdBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    // Get all bookings
    @GetMapping
    public ResponseEntity<List<BookingsDTO>> getAllBookings() {
        List<BookingsDTO> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Get booking by bookingId
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingsDTO> getBookingById(@PathVariable Long bookingId) {
        BookingsDTO booking = bookingService.getBookingById(bookingId);
        return booking != null ? new ResponseEntity<>(booking, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update booking (e.g., change status or flight)
    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingsDTO> updateBooking(@PathVariable Long bookingId, @RequestBody BookingsDTO updatedBooking) {
        BookingsDTO updated = bookingService.updateBooking(bookingId, updatedBooking);
        return updated != null ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Cancel a booking (mark as 'Cancelled')
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
