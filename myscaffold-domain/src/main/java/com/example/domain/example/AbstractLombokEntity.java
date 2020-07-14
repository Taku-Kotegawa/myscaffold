package com.example.domain.example;

import lombok.Data;

@Data
abstract class AbstractLombokEntity {
    private String field001;

    AbstractLombokEntity() {
        System.out.println("AbstractLombokEntity instanced!");
    }
}
