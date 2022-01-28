package com.readingisgood.payload.request;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookQuantityRequest {

    private Integer quantity;
    private Long id;

}
