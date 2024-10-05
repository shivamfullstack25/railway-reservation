package com.railway.ticketing.railway_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railway.ticketing.railway_reservation.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    
}
