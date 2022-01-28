package com.readingisgood.services;

import com.readingisgood.models.OrderDetail;
import com.readingisgood.payload.request.CreateOrderRequest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;

public interface OrderDetailService {

    List<OrderDetail> getOrdersByIntervalDate(LocalDateTime from, LocalDateTime to);

    OrderDetail createNewOrder(CreateOrderRequest request);

    OrderDetail getCustomerOrder(Long customerId);

    Map<Month, List<OrderDetail>> getOrderDetailGroupInMount();

}
