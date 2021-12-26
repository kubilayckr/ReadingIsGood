package com.kubilay.readingisgood.controller.book;

import com.kubilay.readingisgood.model.dto.BookDTO;
import com.kubilay.readingisgood.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Validated
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<Long> createBook(@Valid @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.createBook(bookDTO), HttpStatus.CREATED);
    }

    @GetMapping("/book/{bookNo}")
    public ResponseEntity<BookDTO> getByBookNo(@Positive @PathVariable("bookNo") Long bookNo) {
        return new ResponseEntity<>(bookService.getByBookNo(bookNo), HttpStatus.OK);
    }

    @PatchMapping(path ="/book/{bookNo}")
    public ResponseEntity<Object> addBookStock(@PathVariable("bookNo") Long bookNo, @Positive @RequestParam Integer quantity) {
        bookService.updateStock(bookNo, quantity);
        return new ResponseEntity<>((Object) null, HttpStatus.OK);
    }
}
