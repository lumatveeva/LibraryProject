package com.example.SpringBootLibrary.service;

import com.example.SpringBootLibrary.models.Book;
import com.example.SpringBootLibrary.models.Person;
import com.example.SpringBootLibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll(boolean sortByYear) {
        if(sortByYear){
            return bookRepository.findAll(Sort.by("year"));
        } else {
            return bookRepository.findAll();
        }
    }
    public List<Book> getAllPagableBook(int page, int booksCount, boolean sortByYear) {
        if(sortByYear){
            return bookRepository.findAll(PageRequest.of(page, booksCount, Sort.by("year"))).getContent();
        } else{
            return bookRepository.findAll(PageRequest.of(page, booksCount)).getContent();
        }
    }


    public Book findBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }


    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }
    @Transactional
    public void updateBook(int id, Book updatedBook) {
        Book bookToBeUpdated = bookRepository.findById(id).get();

        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());

        bookRepository.save(updatedBook);
    }

    @Transactional
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void freeTheBook(int id) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                });
    }
    public Person getBookOwner(int id) {
        Optional <Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(Book::getOwner).orElse(null);
    }
    @Transactional
    public void assign(int id, Person selectedPerson) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setTakenAt(new Date());
                }
        );
    }
    public List<Book> findBookByTitle(String title){
        return bookRepository.findBookByTitleStartingWith(title);
    }
}
