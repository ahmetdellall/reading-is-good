package com.readingisgood.service;

import com.readingisgood.exception.ReadingIsGoodApiException;
import com.readingisgood.models.Book;
import com.readingisgood.repository.BookRepository;
import com.readingisgood.services.BookService;
import com.readingisgood.services.BookServiceImpl;
import com.readingisgood.utils.Utils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void createBook_shouldBeSuccess() {
        Book book = Utils.createBook();
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book result = bookService.createBook(book);
        Assert.assertNotNull(result);
    }

    @Test
    public void createBook_shouldBeError_bookNull() {
        expectedException.expect(ReadingIsGoodApiException.class);
        bookService.createBook(null);
    }

    @Test
    public void getBookById_shouldBeSuccess() {
        Book book = Utils.createBook();
        when(bookRepository.findById(book.getId())).thenReturn(java.util.Optional.of(book));
        Book result = bookService.getBookById(book.getId());
        Assert.assertNotNull(result);
    }

    @Test
    public void getBookById_shouldBeError_bookNotFound() {
        expectedException.expect(ReadingIsGoodApiException.class);
        bookService.getBookById(2l);
    }
}
