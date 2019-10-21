package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.model.Orders;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.service.OrdersService;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParkingLotController.class)
class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @MockBean
    private ParkingLotService parkingLotService;

    @MockBean
    private OrdersService ordersService;

    @Test
    void should_save_new_parking_lot_when_post_parking_lot() throws Exception {
        ParkingLot parkingLot= new ParkingLot();
        parkingLot.setName("Dan");
        parkingLot.setCapacity(10);
        parkingLot.setLocation("Manila");

        when(parkingLotService.save(any())).thenReturn(parkingLot);

        ResultActions resultActions = mockMvc.perform(post("/parkingLots")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(parkingLot)));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Dan")))
                .andExpect(jsonPath("$.capacity", is(10)))
                .andExpect(jsonPath("$.location", is("Manila")));
    }

    @Test
    void should_add_new_order_when_post_orders() {
        Orders orders= new Orders();
    }

    @Test
    void list() {
    }

    @Test
    void getParkingLot() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void updateOrders() {
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}