package com.readingisgood.controller;

import com.readingisgood.exception.ErrorCodeEnum;
import com.readingisgood.exception.ReadingIsGoodApiException;
import com.readingisgood.models.Book;
import com.readingisgood.models.BookDTO;
import com.readingisgood.services.BookService;
import com.readingisgood.services.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/book")
@RequiredArgsConstructor
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    @Autowired
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<BookDTO> createbook(@RequestBody @Valid BookDTO bookDTO) {
        try {
            Book book = modelMapper.map(bookDTO, Book.class);
            Book createdBook = bookService.createBook(book);
            BookDTO createdBookDTO = modelMapper.map(createdBook, BookDTO.class);
            LOGGER.info("The Book : {} has been successfully created.", createdBookDTO);
            return ResponseEntity.status(HttpStatus.OK).body(createdBookDTO);
        } catch (Exception e) {
            LOGGER.error("Error : {}", e.getMessage());
            throw new ReadingIsGoodApiException(ErrorCodeEnum.BOOK_NOT_CREATED);
        }
    }

    @GetMapping("get-all-book")
    public ResponseEntity<Page<BookDTO>> getAllbooks(@RequestParam int pageIndex, @RequestParam int pageSize) {
        Page<BookDTO> bookDTOS = bookService.getAllBooks(pageIndex, pageSize).map(entity -> modelMapper.map(entity, BookDTO.class));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }

}
