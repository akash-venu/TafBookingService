package com.teckarch.TafBookingService.Service;


import com.teckarch.TafBookingService.DTO.BookingsDTO;
import com.teckarch.TafBookingService.Service.Interfaces.BookingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final RestTemplate restTemplate;
    private final String datastoreServiceUrl;

    public BookingServiceImpl(RestTemplate restTemplate, @Value("${datastore.service.url}") String datastoreServiceUrl) {
        this.restTemplate = restTemplate;
        this.datastoreServiceUrl = datastoreServiceUrl;
    }

    @Override
    public BookingsDTO createBooking(BookingsDTO booking) {
        // Correctly call the availableSeats endpoint in TafDatastoreService
        String flightUrl = datastoreServiceUrl + "/flights/" + booking.getFlightId();  // Ensure the correct URL path
        Integer availableSeats = restTemplate.getForObject(flightUrl + "/availableSeats", Integer.class);

        if (availableSeats == null || availableSeats <= 0) {
            throw new RuntimeException("Flight is fully booked or not available.");
        }

        // Reduce the available seats by calling the endpoint in TafDatastoreService (ensure it's only called once)
        restTemplate.put(flightUrl + "/reduceSeats", null);

        // Create the booking in TafDatastoreService
        String bookingUrl = datastoreServiceUrl + "/bookings";
        return restTemplate.postForObject(bookingUrl, booking, BookingsDTO.class);
    }

    @Override
    public List<BookingsDTO> getAllBookings() {
        String bookingUrl = datastoreServiceUrl + "/bookings";
        BookingsDTO[] bookingsArray = restTemplate.getForObject(bookingUrl, BookingsDTO[].class);
        return Arrays.asList(bookingsArray);
    }

    @Override
    public BookingsDTO getBookingById(Long bookingId) {
        String bookingUrl = datastoreServiceUrl + "/bookings/" + bookingId;
        return restTemplate.getForObject(bookingUrl, BookingsDTO.class);
    }



    @Override
    public BookingsDTO updateBooking(Long bookingId, BookingsDTO updatedBooking) {
        String bookingUrl = datastoreServiceUrl + "/bookings/" + bookingId;
        restTemplate.put(bookingUrl, updatedBooking);
        return updatedBooking;
    }

    @Override
    public void deleteBooking(Long bookingId) {
        String bookingUrl = datastoreServiceUrl + "/bookings/" + bookingId + "/cancel";
        restTemplate.put(bookingUrl, null);
    }
}


