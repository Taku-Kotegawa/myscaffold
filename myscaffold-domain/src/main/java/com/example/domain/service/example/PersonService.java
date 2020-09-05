package com.example.domain.service.example;


import com.example.domain.example.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PersonService {

    public Person insert(Person person);

    public Person save(Person person);

    public void delete(Person person);

    public Person findOne(Person person);

    public boolean exists(String id);

    public List<Person> findAll(Sort sort);

    public Page<Person> findAll(Pageable pageable);

}
