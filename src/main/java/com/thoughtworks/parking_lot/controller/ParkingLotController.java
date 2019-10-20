package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/parkingLots")
public class ParkingLotController {

    private static final String NOT_FOUND = "Not Found" ;
    private ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.CREATED)
    public ParkingLot add(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.save(parkingLot);
    }

    @GetMapping(produces = {"application/json"})
    public Iterable<ParkingLot> list(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        return parkingLotService.findAll(page, pageSize);
    }

    @GetMapping(value = "/{name}", produces = {"application/json"})
    public ParkingLot getParkingLot(@RequestParam(required = false) String name) {
        return parkingLotService.findByNameContaining(name);
    }

    @DeleteMapping(value = "/{name}", produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<String> delete(@PathVariable String name) throws NotFoundException {
        Optional<ParkingLot> fetchedParkingLot = parkingLotService.findById(name);
        if (fetchedParkingLot.isPresent()) {
            ParkingLot deletedParkingLot = fetchedParkingLot.get();
            parkingLotService.delete(deletedParkingLot);
            return new ResponseEntity<>("Company Deleted",HttpStatus.OK);
        }else
            throw new NotFoundException(NOT_FOUND);
    }

    @PatchMapping(value = "/{name}", produces = {"application/json"})
    public ResponseEntity<ParkingLot> update(@PathVariable String name, @RequestBody ParkingLot parkingLot) {
        Optional<ParkingLot> fetchedParkingLot = parkingLotService.findById(name);
        if (fetchedParkingLot.isPresent()) {
            ParkingLot modifyParkingLot = fetchedParkingLot.get();
            modifyParkingLot.setCapacity(parkingLot.getCapacity());
            ParkingLot savedParkingLot = parkingLotService.save(modifyParkingLot);
            return new ResponseEntity<>(savedParkingLot, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
