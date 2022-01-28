package com.readingisgood.controller;

import com.readingisgood.models.OrderDetail;
import com.readingisgood.payload.request.CreateOrderRequest;
import com.readingisgood.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/order")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderService;

    @PostMapping("/new-order")
    public ResponseEntity<List<OrderDetail>> createNewOrder(@Valid @RequestBody CreateOrderRequest request) {
        return new ResponseEntity(orderService.createNewOrder(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<OrderDetail> getCustomerOrders(@RequestParam Long customerId) {
        return ResponseEntity.ok(orderService.getCustomerOrder(customerId));
    }

    @GetMapping("/get-by-interval/{from}/{to}")
    public ResponseEntity<List<OrderDetail>> getOrderByIntervalTime(@PathVariable(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from, @PathVariable(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atStartOfDay();
        List<OrderDetail> ordersByIntervalDate = orderService.getOrdersByIntervalDate(fromDateTime, toDateTime);
        return ResponseEntity.status(HttpStatus.OK).body(ordersByIntervalDate);
    }
}
