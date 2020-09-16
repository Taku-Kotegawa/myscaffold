package com.example.domain.service.example;

import com.example.domain.example.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class PersonServiceImpl implements PersonService {


    @Override
    public Person insert(Person person) {
        return null;
    }

    @Override
    public Person save(Person person) {
        return null;
    }

    @Override
    public void delete(Person person) {

    }

    @Override
    public Person findOne(Person person) {
        return null;
    }

    @Override
    public boolean exists(String id) {
        return false;
    }

    @Override
    public List<Person> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return null;
    }
}
