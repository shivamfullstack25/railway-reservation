package com.railway.ticketing.railway_reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railway.ticketing.railway_reservation.model.Train;

public interface TrainRepository extends JpaRepository<Train, Long> {
    List<Train> findBySourceAndDestination(String source, String destination);
    
}
