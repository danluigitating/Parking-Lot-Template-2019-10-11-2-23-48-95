package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.Orders;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.OrdersService;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/parkingLots")
public class ParkingLotController {

    private static final String NOT_FOUND = "Not Found" ;

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private OrdersService ordersService;

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.CREATED)
    public ParkingLot add(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.save(parkingLot);
    }

     @PostMapping(value = "/{name}/orders", produces = {"application/json"})
     @ResponseStatus(code = HttpStatus.CREATED)
     public ResponseEntity<Orders> addOrder(@PathVariable String name , @RequestBody Orders orders) {
         Optional<ParkingLot> fetchedParkingLot = parkingLotService.findById(name);

         if (fetchedParkingLot.isPresent()) {
             Orders savedOrders= ordersService.save(name, orders);
             return new ResponseEntity<>(savedOrders, HttpStatus.OK);
         }
         else
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }

    @GetMapping(produces = {"application/json"})
    public Iterable<ParkingLot> list(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        return parkingLotService.findAll(page, pageSize);
    }

    @GetMapping(value = "/{name}", produces = {"application/json"})
    public ParkingLot getParkingLot(@PathVariable String name) {
        return parkingLotService.findByNameContaining(name);
    }

    @DeleteMapping(value = "/{name}", produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<String> delete(@PathVariable String name) throws NotFoundException {
        Optional<ParkingLot> fetchedParkingLot = parkingLotService.findById(name);
        if (fetchedParkingLot.isPresent()) {
            ParkingLot deletedParkingLot = fetchedParkingLot.get();
            parkingLotService.delete(deletedParkingLot);
            return new ResponseEntity<>("Parking Lot Deleted",HttpStatus.OK);
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

    @PatchMapping(value = "/{name}/orders/{plateNum}", produces = {"application/json"})
    public ResponseEntity<Orders> updateOrders(@PathVariable String name, @PathVariable String plateNum, @RequestBody ParkingLot parkingLot) {
        Optional<ParkingLot> fetchedParkingLot = parkingLotService.findById(name);
        Optional<Orders> fetchedOrders= Optional.ofNullable(ordersService.findByPlateNumber(plateNum));
        if (fetchedParkingLot.isPresent() && fetchedOrders.isPresent()) {

            LocalDateTime dateTime = LocalDateTime.now();

            Orders modifyOrders = fetchedOrders.get();
            modifyOrders.setCloseTime(dateTime.toString());
            modifyOrders.setOrderStatus("CLOSED");
            Orders savedOrders = ordersService.saveModified(modifyOrders);
            return new ResponseEntity<>(savedOrders, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
