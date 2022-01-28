package com.readingisgood.services;

import com.readingisgood.exception.ErrorCodeEnum;
import com.readingisgood.exception.ReadingIsGoodApiException;
import com.readingisgood.models.Book;
import com.readingisgood.payload.request.UpdateBookQuantityRequest;
import com.readingisgood.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book createBook(Book book) {
        return bookRepository.save(book);

    }

    @Transactional
    public void updateBookQuantity(UpdateBookQuantityRequest updateBookQuantityRequest) {
        if (Objects.nonNull(updateBookQuantityRequest.getId())) {
            Book book = getBookById(updateBookQuantityRequest.getId());
            int differance = book.getQuantity() - updateBookQuantityRequest.getQuantity();
            if (differance > 0) {
                book.setQuantity(differance);

                bookRepository.save(book);
            } else {
                throw new ReadingIsGoodApiException(ErrorCodeEnum.BOOK_NOT_AVAILABLE);
            }
        } else {
            throw new ReadingIsGoodApiException(ErrorCodeEnum.BOOK_ID_MUST_NOT_BLANK);
        }
    }

    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new ReadingIsGoodApiException(ErrorCodeEnum.BOOK_NOT_FOUND);
        }
    }

    public Page<Book> getAllBooks(int pageIndex, int pageSize) {
        return bookRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    public Map<Month, List<Book>> getAllBookGroupInMount() {
        return bookRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(request ->
                        request.getCreateDateTime().getMonth()));
    }
}
