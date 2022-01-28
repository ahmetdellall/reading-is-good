package com.readingisgood.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDTO {

    private String month;
    private int totalOrderCount;
    private int totalBookCount;
    private double totalPurchasedAmount;

}
