package com.readingisgood.controller;

import com.readingisgood.exception.ErrorCodeEnum;
import com.readingisgood.exception.ReadingIsGoodApiException;
import com.readingisgood.models.Book;
import com.readingisgood.models.OrderDetail;
import com.readingisgood.models.StatisticDTO;
import com.readingisgood.services.BookServiceImpl;
import com.readingisgood.services.OrderDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @Autowired
    private OrderDetailServiceImpl orderDetailServiceImpl;

    @GetMapping("{customer-id}")
    public ResponseEntity<List<StatisticDTO>> getMonthlyReport(@PathVariable(value = "customer-id") Long customerId) {
        try {
            List<StatisticDTO> statisticDTOS = createdReport(customerId);
            LOGGER.info("Statistics report created successfully");
            return ResponseEntity.status(HttpStatus.OK).body(statisticDTOS);
        } catch (Exception e) {
            LOGGER.error("Error : {}", e.getMessage());
            throw new ReadingIsGoodApiException(ErrorCodeEnum.STATISTICS_ERROR);
        }
    }


    private List<StatisticDTO> createdReport(Long customerId) {

        List<StatisticDTO> statisticDTOS = new ArrayList<>();

        Map<Month, List<Book>> allBookCount = bookServiceImpl.getAllBookGroupInMount();
        Map<Month, List<OrderDetail>> totalOrderDetailGroup = orderDetailServiceImpl.getOrderDetailGroupInMount(customerId);

        Map<Month, Integer> allBookCountGroup = allBookCount.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .filter(Objects::nonNull)
                                .map(Book::getQuantity)
                                .reduce(0, Integer::sum)));

        Map<Month, Integer> allOrderCountGroup = totalOrderDetailGroup.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .filter(Objects::nonNull)
                                .map(orderDetail -> orderDetail.getBooks().size())
                                .reduce(0, Integer::sum))
                );


        Map<Month, Double> purchaseGroup = totalOrderDetailGroup.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .filter(Objects::nonNull)
                                .map(OrderDetail::getTotalPrice)
                                .reduce(0.0, Double::sum))
                );

        Arrays.stream(Month.values())
                .forEach(month -> {
                    StatisticDTO statisticDTO = new StatisticDTO();
                    Integer bookCount = 0;
                    if (allBookCountGroup.get(month) != null) {
                        bookCount = allBookCountGroup.get(month);
                    }

                    Integer orderCount = 0;
                    if (allOrderCountGroup.get(month) != null) {
                        orderCount = allOrderCountGroup.get(month);
                    }

                    Double purchase = 0.0;
                    if (purchaseGroup.get(month) != null) {
                        purchase = purchaseGroup.get(month);
                    }
                    statisticDTO.setTotalPurchasedAmount(purchase);
                    statisticDTO.setTotalOrderCount(orderCount);
                    statisticDTO.setTotalBookCount(bookCount);
                    statisticDTO.setMonth(month.name());
                    statisticDTOS.add(statisticDTO);
                });
        return statisticDTOS;
    }
}
