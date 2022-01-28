package com.readingisgood.utils;

import com.readingisgood.models.Book;
import com.readingisgood.models.Customer;
import com.readingisgood.models.OrderBooks;
import com.readingisgood.models.OrderDetail;
import com.readingisgood.payload.request.CreateOrderRequest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Utils {

    public static final int QUANTITY = 10;
    public static final double PRICE = 10.0;
    public static final double TOTAL_PRICE = 200.0;

    public static String generateRandomString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public static Customer createCustomer() {
        Customer customer = new Customer();
        customer.setAddress(generateRandomString());
        customer.setEmail(generateRandomString());
        customer.setName(generateRandomString());
        return customer;
    }

    public static Book createBook() {
        Book book = new Book();
        book.setQuantity(QUANTITY);
        book.setName(generateRandomString());
        book.setPrice(PRICE);
        return book;
    }

    public static OrderDetail createOrderDetail() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setBooks(Arrays.asList(createBook()));
        orderDetail.setCustomerId(createCustomer().getId());
        orderDetail.setTotalPrice(TOTAL_PRICE);
        return orderDetail;
    }


    public static CreateOrderRequest createOrderRequest() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCustomerId(createCustomer().getId());
        OrderBooks orderBooks = new OrderBooks();
        orderBooks.setBookId(createBook().getId());
        orderBooks.setQuantity(QUANTITY);
        createOrderRequest.setBooks(List.of(orderBooks));
        return createOrderRequest;
    }

}
