package com.kubilay.readingisgood.service.book.impl;

import com.kubilay.readingisgood.entity.book.Book;
import com.kubilay.readingisgood.exception.BusinessException;
import com.kubilay.readingisgood.mapper.book.BookMapper;
import com.kubilay.readingisgood.model.dto.BookDTO;
import com.kubilay.readingisgood.repository.book.BookRepository;
import com.kubilay.readingisgood.service.SequenceGeneratorServiceImpl;
import com.kubilay.readingisgood.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static com.kubilay.readingisgood.exception.BusinessException.ServiceException.BOOK_NOT_FOUND;
import static com.kubilay.readingisgood.exception.BusinessException.ServiceException.BOOK_QUANTITY_BELOW_ZERO;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final SequenceGeneratorServiceImpl sequenceGenerator;
    private final MessageSource messageSource;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Long createBook(BookDTO bookDTO) {
        Book book = bookMapper.toModel(bookDTO);
        long id = sequenceGenerator.generateSequence(Book.SEQUENCE_NAME);
        book.setId(id);
        book.setBookNo(id);
        bookRepository.save(book);
        return id;
    }

    public BookDTO getByBookNo(Long bookNo) {
        Book book = bookRepository.getBookByBookNo(bookNo);
        if (book == null) {
            throw new BusinessException(messageSource.getMessage(BOOK_NOT_FOUND.getKey(), new Object[] {bookNo},
                    Locale.getDefault()) , BOOK_NOT_FOUND.getStatus());
        }
        return bookMapper.toDTO(book);
    }

    @Transactional
    public Long updateStock(Long bookNo, Integer quantity) {
        Book book = bookRepository.getBookByBookNo(bookNo);
        if (book == null) {
            throw new BusinessException(messageSource.getMessage(BOOK_NOT_FOUND.getKey(), new Object[] {bookNo},
                    Locale.getDefault()) , BOOK_NOT_FOUND.getStatus());
        } else if(book.getQuantity() + quantity < 0) {
            throw new BusinessException(messageSource.getMessage(BOOK_QUANTITY_BELOW_ZERO.getKey(), new Object[] {bookNo},
                    Locale.getDefault()), BOOK_QUANTITY_BELOW_ZERO.getStatus());
        }
        book.setQuantity(book.getQuantity() + quantity);
        return bookRepository.save(book).getId();
    }
}
