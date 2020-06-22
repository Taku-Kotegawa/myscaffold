package com.example.app.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ListItem implements Serializable {

    private String value;

    private String label;

}
