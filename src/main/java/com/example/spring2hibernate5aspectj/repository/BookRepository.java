package com.example.spring2hibernate5aspectj.repository;

import com.example.spring2hibernate5aspectj.entity.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "withAuthor", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select b from Book b where b.id = ?1")
    Optional<Book> findByIdWithAuthor(Long id);
}
