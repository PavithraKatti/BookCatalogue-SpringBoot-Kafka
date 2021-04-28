package com.book.catalogue.controller;

import com.book.catalogue.service.BookService;
import com.book.catalogue.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private KafkaTemplate<String, Book> kafkaTemplate;

    private static final String TOPIC = "Kafka_Book_Catalogue";

    @GetMapping
    private ResponseEntity<Iterable<Book>> list() {
        Iterable<Book> issues = bookService.books();
        return ResponseEntity.ok(issues);
    }

    @PostMapping
    private ResponseEntity<Book> create(@RequestBody Book book) {
        Book saved = bookService.create(book);
        kafkaTemplate.send(TOPIC, "Book Added",saved);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Book> update(@PathVariable int id, @RequestBody Book book) {
        Book updated = bookService.update(id, book);
        kafkaTemplate.send(TOPIC, "Book Updated",updated);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity delete(@PathVariable int id) {
        Optional<Book> deletedBook = bookService.getById(id);
        if(deletedBook.isPresent()){
            kafkaTemplate.send(TOPIC, "Book Deleted", deletedBook.get());
            bookService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/filter")
    private ResponseEntity<Iterable<Book>> getBooksByFilter(@RequestParam(required = false) String title,
                                                            @RequestParam(required = false) BigInteger isbn,
                                                            @RequestParam(required = false) String author) {
        if(!StringUtils.isEmpty(title)){
            return ResponseEntity.ok(bookService.filterByTitle(title));
        }else if(isbn != null){
            return ResponseEntity.ok(bookService.filterByISBN(isbn));
        }else if(!StringUtils.isEmpty(author)){
            return ResponseEntity.ok(bookService.filterByAuthor(author));
        }
        return ResponseEntity.ok(bookService.books());
    }
}
