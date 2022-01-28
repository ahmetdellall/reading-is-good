package com.readingisgood.models;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBooks {

    private Long bookId;
    private Integer quantity;
}
