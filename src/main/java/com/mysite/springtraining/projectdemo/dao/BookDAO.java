package com.mysite.springtraining.projectdemo.dao;

import com.mysite.springtraining.projectdemo.model.Book;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book(name, author, year) values (?,?,?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public Book show(int id) {
        return jdbcTemplate.query("select * from book where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void giveToPerson(int id, int personId) {
        jdbcTemplate.update("update book set person_id=? where id=?",
                personId, id);
    }

    public void takeFromPerson(int id) {
        jdbcTemplate.update("update book set person_id=null where id=?", id);
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("update book set name=?, author=?, year=? where id=?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from book where id=?", id);
    }
}
