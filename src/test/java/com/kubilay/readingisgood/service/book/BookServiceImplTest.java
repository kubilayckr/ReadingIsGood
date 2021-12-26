package com.kubilay.readingisgood.service.book;

import com.kubilay.readingisgood.entity.book.Book;
import com.kubilay.readingisgood.exception.BusinessException;
import com.kubilay.readingisgood.mapper.book.BookMapper;
import com.kubilay.readingisgood.model.dto.BookDTO;
import com.kubilay.readingisgood.repository.book.BookRepository;
import com.kubilay.readingisgood.service.SequenceGeneratorServiceImpl;
import com.kubilay.readingisgood.service.book.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    SequenceGeneratorServiceImpl sequenceGenerator;

    @Mock
    MessageSource messageSource;

    @Mock
    BookRepository bookRepository;

    @Spy
    BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    void test_CreateBook(){
        BookDTO bookDTO = generateBookDTO();
        Book book = generateBook();
        when(sequenceGenerator.generateSequence(any())).thenReturn(1L);
        when(bookRepository.save(any())).thenReturn(book);
        assertEquals(1L, bookService.createBook(bookDTO));
    }

    @Test
    void test_GetByBookNo_NotNull(){
        Book book = generateBook();
        when(bookRepository.getBookByBookNo(any())).thenReturn(book);
        BookDTO bookDTO = bookService.getByBookNo(1L);
        assertEquals(book.getBookNo(), bookDTO.getBookNo());
    }

    @Test
    void test_GetByBookNo_Null(){
        when(bookRepository.getBookByBookNo(any())).thenReturn(null);
        assertThrows(BusinessException.class, () -> bookService.getByBookNo(1L));
    }

    @Test
    void test_UpdateStock_NotNull(){
        Book book = generateBook();
        when(bookRepository.getBookByBookNo(any())).thenReturn(book);
        when(bookRepository.save(any())).thenReturn(book);
        assertEquals(book.getBookNo(), bookService.updateStock(1L, 1));
    }

    @Test
    void test_UpdateStock_NotEnoughQuantity(){
        Book book = generateBook();
        when(bookRepository.getBookByBookNo(any())).thenReturn(book);
        assertThrows(BusinessException.class, () -> bookService.updateStock(1L, -11));
    }

    @Test
    void test_UpdateStock_Null(){
        when(bookRepository.getBookByBookNo(any())).thenReturn(null);
        assertThrows(BusinessException.class, () -> bookService.getByBookNo(1L));
    }

    private BookDTO generateBookDTO() {
        return new BookDTO(null, null, "Title", "Author", "Publisher", 10, BigDecimal.TEN);
    }

    private Book generateBook() {
        return new Book(1L, 1L, 1L, "Title", "Author", "Publisher", 10, BigDecimal.TEN);
    }
}
