package com.example.app.welcome;

import com.example.domain.service.account.AccountSharedService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({@ContextConfiguration({"classpath:META-INF/spring/applicationContext.xml", "classpath:META-INF/spring/spring-security.xml"}), @ContextConfiguration("classpath:META-INF/spring/spring-mvc.xml")})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HelloControllerTest {

    @Inject
    WebApplicationContext webApplicationContext;

    @Autowired
    AccountSharedService accountSharedService;

    MockMvc mockMvc;

    @BeforeAll
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(log()).build();
    }

    @Test
    void home() throws Exception {

        ResultActions results = mockMvc.perform(get("/"));
        System.out.println(results);

    }
}
