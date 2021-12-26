package com.kubilay.readingisgood.repository.book;

import com.kubilay.readingisgood.entity.book.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, Long> {

    Book getBookByBookNo(Long bookNo);
}
