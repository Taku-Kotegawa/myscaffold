package com.example.domain.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class TestForm {

    private String stringField;

    private Integer integerField;

    private Long longField;

    private Boolean booleanField;

    private Date dateField;

    private String[] arrayStringField;

    private LocalDateTime localDateTimeField;

    private List<String> listStringField;

    private Map<String, String> mapStringField;

    private OtherClass otherClassField;

    class OtherClass {
        private String name;
    }
}
