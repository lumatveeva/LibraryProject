package com.example.SpringBootLibrary.repository;


import com.example.SpringBootLibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    public List<Book> findBookByTitleStartingWith(String title);
}
