package com.readingisgood.services;

import com.readingisgood.models.Book;
import com.readingisgood.payload.request.UpdateBookQuantityRequest;
import org.springframework.data.domain.Page;

import java.time.Month;
import java.util.List;
import java.util.Map;

public interface BookService {

    void updateBookQuantity(UpdateBookQuantityRequest updateBookQuantityRequest);

    Book createBook(Book book);

    Book getBookById(Long id);

    Map<Month, List<Book>> getAllBookGroupInMount();

    Page<Book> getAllBooks(int pageIndex, int pageSize);
}
