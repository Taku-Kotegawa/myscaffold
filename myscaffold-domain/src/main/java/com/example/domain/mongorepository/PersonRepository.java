package com.example.domain.mongorepository;

import com.example.domain.example.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {

}
