package com.example.spring2hibernate5aspectj.repository;

import com.example.spring2hibernate5aspectj.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
