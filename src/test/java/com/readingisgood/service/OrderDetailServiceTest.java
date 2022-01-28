package com.readingisgood.service;

import com.readingisgood.exception.ReadingIsGoodApiException;
import com.readingisgood.models.Book;
import com.readingisgood.models.Customer;
import com.readingisgood.models.OrderDetail;
import com.readingisgood.payload.request.CreateOrderRequest;
import com.readingisgood.repository.OrderDetailRepository;
import com.readingisgood.services.*;
import com.readingisgood.utils.Utils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailServiceTest {

    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    @Mock
    OrderDetailRepository orderDetailRepository;

    @Mock
    CustomerServiceImpl customerService;

    @Mock
    BookServiceImpl bookService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void createNewOrder_shouldBeSuccess() {
        Customer customer = Utils.createCustomer();
        Book book = Utils.createBook();
        when(customerService.getCustomerById(customer.getId())).thenReturn(Optional.of(customer));
        when(bookService.getBookById(book.getId())).thenReturn(book);
        OrderDetail result = orderDetailService.createNewOrder(Utils.createOrderRequest());
        Assert.assertNotNull(result);
    }

    @Test
    public void createNewOrder_shouldBeError_BookEmpty() {
        expectedException.expect(ReadingIsGoodApiException.class);
        Customer customer = Utils.createCustomer();
        when(customerService.getCustomerById(customer.getId())).thenReturn(Optional.of(customer));
        CreateOrderRequest request = Utils.createOrderRequest();
        request.setBooks(null);
        orderDetailService.createNewOrder(request);

    }

    @Test
    public void getCustomerOrder_shouldBeSuccess() {
        Customer customer = Utils.createCustomer();
        when(orderDetailRepository.findByCustomerId(customer.getId())).thenReturn(java.util.Optional.of(Utils.createOrderDetail()));
        OrderDetail result = orderDetailService.getCustomerOrder(customer.getId());
        Assert.assertNotNull(result);
    }

    @Test
    public void getCustomerOrder_shouldBeError_orderNotFound() {
        expectedException.expect(ReadingIsGoodApiException.class);
        Customer customer = Utils.createCustomer();
        when(orderDetailRepository.findByCustomerId(customer.getId())).thenReturn(null);
        orderDetailService.getCustomerOrder(customer.getId());
    }

}
