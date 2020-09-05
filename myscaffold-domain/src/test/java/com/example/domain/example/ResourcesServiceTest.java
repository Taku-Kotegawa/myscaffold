package com.example.domain.example;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class ResourcesServiceTest {


    // https://qiita.com/rubytomato@github/items/bfeadd1e9f8d801e6d9e
    @Test
    void test1() {

        Resource resource = new ClassPathResource("META-INF/spring/myscaffold-infra.xml");
        Boolean result = resource.exists();

        InputStream in = null;
        try {
            in = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while (true) {
                if (!((line = reader.readLine()) != null)) break;
                sb.append(line);
            }
            System.out.println(sb);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}