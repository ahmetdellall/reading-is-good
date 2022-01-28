package com.readingisgood.payload.request;


import com.readingisgood.models.OrderBooks;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    private List<OrderBooks> books;
    private Long customerId;

}
