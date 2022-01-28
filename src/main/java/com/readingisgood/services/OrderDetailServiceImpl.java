package com.readingisgood.services;

import com.readingisgood.controller.CustomerController;
import com.readingisgood.exception.ErrorCodeEnum;
import com.readingisgood.exception.ReadingIsGoodApiException;
import com.readingisgood.models.Book;
import com.readingisgood.models.Customer;
import com.readingisgood.models.OrderBooks;
import com.readingisgood.models.OrderDetail;
import com.readingisgood.payload.request.CreateOrderRequest;
import com.readingisgood.payload.request.UpdateBookQuantityRequest;
import com.readingisgood.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailServiceImpl.class);

    private final OrderDetailRepository orderDetailRepository;
    private final BookService bookService;
    private final CustomerService customerService;


    public List<OrderDetail> getOrdersByIntervalDate(LocalDateTime from, LocalDateTime to) {
        List<OrderDetail> all = orderDetailRepository.findAll();
        return all.stream()
                .filter(orderDetail -> orderDetail.getCreatedDateTime().compareTo(from) * to.compareTo(orderDetail.getCreatedDateTime()) >= 0)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDetail createNewOrder(CreateOrderRequest request) {
        OrderDetail detail = new OrderDetail();
        List<Book> bookList = new ArrayList<>();
        double totalPrice = 0.0;
        Optional<Customer> customer = customerService.getCustomerById(request.getCustomerId());
        if (customer.isPresent() && !CollectionUtils.isEmpty(request.getBooks())) {
            for (OrderBooks book : request.getBooks()) {
                Book entityBook = bookService.getBookById(book.getBookId());
                totalPrice += calculatePrice(book, entityBook.getPrice());
                bookList.add(entityBook);
                updateStockRecords(book);
                entityBook.setQuantity(book.getQuantity());
            }
            detail.setCustomerId(customer.get().getId());
            detail.setTotalPrice(totalPrice);
            detail.setBooks(bookList);
            LOGGER.info("Order save successfully");
            orderDetailRepository.save(detail);
        } else {
            LOGGER.info(ErrorCodeEnum.BOOK_NOT_FOUND.getMessage());
            throw new ReadingIsGoodApiException(ErrorCodeEnum.BOOK_NOT_FOUND);
        }
        return detail;
    }


    private Double calculatePrice(OrderBooks book, Double price) {
        return book.getQuantity() * price;
    }

    private void updateStockRecords(OrderBooks book) {
        UpdateBookQuantityRequest request = new UpdateBookQuantityRequest();
        request.setId(book.getBookId());
        request.setQuantity(book.getQuantity());
        bookService.updateBookQuantity(request);
    }


    public OrderDetail getCustomerOrder(Long customerId) {
        Optional<OrderDetail> orderDetail = orderDetailRepository.findByCustomerId(customerId);
        if (orderDetail.isPresent()) {
            return orderDetail.get();
        } else {
            throw new ReadingIsGoodApiException(ErrorCodeEnum.ORDER_NOT_FOUND);
        }
    }

    public Map<Month, List<OrderDetail>> getOrderDetailGroupInMount() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();

        return orderDetails.stream()
                .collect(Collectors.groupingBy(request ->
                        request.getCreatedDateTime().getMonth()));

    }
}
