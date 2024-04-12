package com.example.SpringBootLibrary.controllers;

import com.example.SpringBootLibrary.models.Book;
import com.example.SpringBootLibrary.models.Person;
import com.example.SpringBootLibrary.service.BookService;
import com.example.SpringBootLibrary.service.PeopleService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private BookService bookService;



    @GetMapping()
    public String getAllBook(Model model,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "books_per_page", required = false) Integer booksCount,
                                 @RequestParam(value = "sortByYear", required = false) boolean sortByYear){
        if(page == null || booksCount == null){
            model.addAttribute("books", bookService.findAll(sortByYear));
        } else {
            model.addAttribute("books", bookService.getAllPagableBook(page, booksCount,sortByYear));
            }
        return "books/allBook";
    }



    @GetMapping("/{id}")
    public String findBookByIndex(@PathVariable("id") int id, Model model, @ModelAttribute("Person") Person person){
        model.addAttribute("book",bookService.findBookById(id));
        Person owner = bookService.getBookOwner(id);
        List<Person> allPerson = peopleService.getAllPerson();
        if(person != null){
            model.addAttribute("owner", owner);
        } else{
            model.addAttribute("man", allPerson);
        }
        return "books/index";
    }


    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }
    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "books/new";
        }
        bookService.save(book);
        return "redirect:books";
    }
    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookService.findBookById(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                             @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookService.updateBook(id,book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.deleteBook(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson){

        bookService.assign(id, selectedPerson);
        return "redirect:/books";
    }
    @GetMapping("/{id}/free")
    public String getBookFree( @PathVariable("id") int id){
        bookService.freeTheBook(id);
        return "redirect:/books";
    }
    @GetMapping("/search")
    public String searchPage(){
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query){
        model.addAttribute("books", bookService.findBookByTitle(query));
        return "books/search";
    }
}
