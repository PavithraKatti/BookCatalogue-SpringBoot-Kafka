package com.book.catalogue.repository;

import com.book.catalogue.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByISBN(BigInteger isbn);
}