package com.example.spring2hibernate5aspectj.repository;

import com.example.spring2hibernate5aspectj.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
