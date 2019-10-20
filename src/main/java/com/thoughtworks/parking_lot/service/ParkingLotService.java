package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingLot save(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public Iterable<ParkingLot> findAll(Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        if (page != null && pageSize != null) {
            pageRequest = PageRequest.of(page, pageSize, Sort.by("name").ascending());
        }

        return parkingLotRepository.findAll(pageRequest);
    }

    public Optional<ParkingLot> findById(String name) {
        return parkingLotRepository.findById(name);
    }

    public void delete(ParkingLot deletedParkingLot) {
        parkingLotRepository.delete(deletedParkingLot);
    }

    public ParkingLot findByNameContaining(String name) {
        return parkingLotRepository.findByNameContaining(name);
    }
}
