package com.example.domain.example;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LombokEntity extends AbstractLombokEntity {

    private String field101;
    private Integer field102;
    private List<String> list103;
    private Map<String, String> map104;
    private LombokChildEntity childField105;
    private List<LombokChildEntity> childList106;

}
