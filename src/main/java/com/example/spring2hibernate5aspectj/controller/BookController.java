package com.example.spring2hibernate5aspectj.controller;

import com.example.spring2hibernate5aspectj.entity.Author;
import com.example.spring2hibernate5aspectj.entity.Book;
import com.example.spring2hibernate5aspectj.repository.AuthorRepository;
import com.example.spring2hibernate5aspectj.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final EntityManager entityManager;
    private final AuthorRepository authorRepository;

    @GetMapping("/{id}")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
    public String getBook(@PathVariable Long id) {
        return bookRepository.findById(id).map(Book::getTitle).orElse(null);
    }

    @GetMapping("/v2/{id}")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
    public String getBookV2(@PathVariable Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Author author = bookOptional.get().getAuthor();
            return "Author: " + author.getFirstName() + " " + author.getLastName();
        } else {
            return "Not found";
        }
    }

    @PostMapping
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED)
    public Long addBook() {

        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe" + UUID.randomUUID());
        authorRepository.save(author);

        Book book = new Book();
        book.setTitle(UUID.randomUUID().toString());
        book.setContent("fucking the shit, i not want".getBytes(StandardCharsets.UTF_8));
        book.setAuthor(author);
        author.getBooks().add(book);
        book = bookRepository.save(book);
        return book.getId();
    }
}
