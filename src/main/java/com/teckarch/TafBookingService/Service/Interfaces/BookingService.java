package com.teckarch.TafBookingService.Service.Interfaces;

import com.teckarch.TafBookingService.DTO.BookingsDTO;

import java.util.List;

public interface BookingService {
    BookingsDTO createBooking(BookingsDTO bookings);
    List<BookingsDTO> getAllBookings();
    BookingsDTO getBookingById(Long bookingId);
    BookingsDTO updateBooking(Long bookingId,BookingsDTO updatedBookings);
    void deleteBooking(Long bookingId);
}
