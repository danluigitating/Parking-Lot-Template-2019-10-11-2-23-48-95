package com.thoughtworks.parking_lot.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ParkingLot {
    @Id
    private String name;

    private Integer capacity;
    private String location;

    @OneToMany(cascade= CascadeType.ALL)
    private List<Orders> orders;

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
