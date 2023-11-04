package com.mysite.springtraining.projectdemo.util;

import com.mysite.springtraining.projectdemo.dao.PersonDAO;
import com.mysite.springtraining.projectdemo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // посмотреть, есть ли человек с таким же ФИО в БД
        if (personDAO.show(person.getName()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}
