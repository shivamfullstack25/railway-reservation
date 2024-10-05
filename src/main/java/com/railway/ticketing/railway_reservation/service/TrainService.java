package com.railway.ticketing.railway_reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.ticketing.railway_reservation.model.Train;
import com.railway.ticketing.railway_reservation.repository.TrainRepository;

@Service
public class TrainService {
    @Autowired
    private TrainRepository trainRepository;

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public Train saveTrain(Train train) {
        return trainRepository.save(train);
    }

    public List<Train> findTrains(String source, String destination) {
        return trainRepository.findBySourceAndDestination(source, destination);
    }
}

