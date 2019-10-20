package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.Orders;
import com.thoughtworks.parking_lot.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    public Orders save(String name, Orders orders) {

        LocalDateTime dateTime = LocalDateTime.now();

        orders.setName(name);
        orders.setCreationTime(dateTime.toString());
        orders.setCloseTime("NULL");
        orders.setOrderStatus("OPEN");

        return ordersRepository.save(orders);
    }

    public Orders saveModified(Orders orders){
        return ordersRepository.save(orders);
    }

    public Orders findByPlateNumber(String plateNum) {
        return ordersRepository.findByNameContaining(plateNum);
    }
}
