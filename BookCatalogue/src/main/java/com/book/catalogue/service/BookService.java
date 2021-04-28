package com.book.catalogue.service;

import com.book.catalogue.repository.BookRepository;
import com.book.catalogue.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Iterable<Book> books() {
        return bookRepository.findAll();
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book update(int id, Book book) {
        Optional<Book> currentBook = bookRepository.findById(id);
        if(currentBook.isPresent()){
            currentBook.get().setTitle(book.getTitle());
            currentBook.get().setAuthor(book.getAuthor());
            currentBook.get().setISBN(book.getISBN());
            currentBook.get().setPublishdate(book.getPublishdate());
        }
        return bookRepository.save(currentBook.get());
    }

    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> getById(int id) {
        return bookRepository.findById(id);
    }

    public Iterable<Book> filterByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Iterable<Book> filterByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public Iterable<Book> filterByISBN(BigInteger isbn) {
        return bookRepository.findByISBN(isbn);
    }
}
