package com.mysite.springtraining.projectdemo.dao;

import com.mysite.springtraining.projectdemo.model.Book;
import com.mysite.springtraining.projectdemo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(name, year_of_birth) values (?,?)",
                person.getName(), person.getYearOfBirth());
    }

    public Optional<Person> show(String name) {
        return jdbcTemplate.query("select * from person where name=?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public Person show(int id) {
        return jdbcTemplate.query("select * from person where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("update person set name=?, year_of_birth=? where id=?",
                updatedPerson.getName(), updatedPerson.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where id=?", id);
    }

    public List<Book> getPersonBooks(int id) {
        return jdbcTemplate.query("select * from book where person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}
