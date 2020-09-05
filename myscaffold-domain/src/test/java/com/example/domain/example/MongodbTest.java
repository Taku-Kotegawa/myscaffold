package com.example.domain.example;

import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
public class MongodbTest {

    @Test
    void test001() {

        MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "testdb");
        mongoOps.insert(new Person("Joe", 34));
        Person result = mongoOps.findOne(new Query(where("name").is("Joe")), Person.class);
        log.info(result.toString());

        MongoDbFactory mongoDbFactory;

        mongoOps.dropCollection("person");

    }





































}
