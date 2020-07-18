package com.example.app.example.authentication;


import com.example.domain.example.AbstractLombokEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AuthenticationTestForm extends AbstractLombokEntity {

    private String test001;
    private Integer test002;
    private String[] test011;
    private List<String> test101;
}
