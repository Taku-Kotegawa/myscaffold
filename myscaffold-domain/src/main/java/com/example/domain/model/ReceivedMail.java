package com.example.domain.model;

import lombok.Data;

@Data
public class ReceivedMail {
    private String from;
    private String to;
    private String subject;
    private String text;
}
