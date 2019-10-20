package com.thoughtworks.parking_lot.repository;
import com.thoughtworks.parking_lot.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository <Orders, String> {
    Orders findByNameContaining(String name);
}