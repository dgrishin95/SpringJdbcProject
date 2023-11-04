package com.mysite.springtraining.projectdemo.controller;

import com.mysite.springtraining.projectdemo.dao.BookDAO;
import com.mysite.springtraining.projectdemo.dao.PersonDAO;
import com.mysite.springtraining.projectdemo.model.Book;
import com.mysite.springtraining.projectdemo.model.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());

        return "books/index";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.show(id);
        Integer personId = book.getPersonId();

        model.addAttribute("book", book);
        model.addAttribute("people", personDAO.index());

        if (personId != null) {
            model.addAttribute("person", personDAO.show(personId));
        } else {
            model.addAttribute("person", new Person());
        }

        return "books/show";
    }

    @PatchMapping("/{id}/give")
    public String giveToPerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.giveToPerson(id, person.getId());
        return "redirect:/books";
    }

    @PatchMapping("/{id}/take")
    public String takeFromPerson(@PathVariable("id") int id) {
        bookDAO.takeFromPerson(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
