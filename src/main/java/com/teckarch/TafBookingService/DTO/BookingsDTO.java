package com.teckarch.TafBookingService.DTO;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class BookingsDTO {

    private Long bookingId;
    private Long userId;
    private Long flightId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
