package com.readingisgood.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    private Long id;

    private LocalDateTime createDateTime;

    Long customerId;

    Double totalPrice;

    private List<BookDTO> books = new ArrayList<>();
}
