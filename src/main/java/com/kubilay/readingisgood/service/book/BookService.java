package com.kubilay.readingisgood.service.book;

import com.kubilay.readingisgood.model.dto.BookDTO;

public interface BookService {

    Long createBook(BookDTO bookDTO);

    BookDTO getByBookNo(Long bookNo);

    Long updateStock(Long bookNo, Integer quantity);
}
