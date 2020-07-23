package com.example.app.example.authentication;

import com.example.domain.example.AuthenticationTestService;
import com.example.domain.service.userdetails.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

import static com.example.domain.common.AuthMapUtils.*;

@Controller
@RequestMapping("authenticationtest")
public class AuthenticationTestController {

    @Autowired
    AuthenticationTestService authenticationTestService;

    @RequestMapping(method = RequestMethod.GET)
    public String init() {
        return "example/authenticationtest";
    }

    @GetMapping("test")
    public String test(Model model) {
        authenticationTestService.checkAuthenticationTest();
        return "example/authenticationtest";
    }


    @GetMapping("test2")
    public String test2(Model model) {

        authenticationTestService.checkAuthenticationTest();

        return "example/authenticationtest";
    }

    @GetMapping("test3")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String test3(Model model) {
        return "example/authenticationtest";
    }

    @GetMapping("test4")
    public String test4(@AuthenticationPrincipal LoggedInUser loggedInUser) {
        authenticationTestService.checkAuthenticationTest2(loggedInUser);
        return "example/authenticationtest";
    }

    @ModelAttribute
    public AuthenticationTestForm setUp() {
        return new AuthenticationTestForm();
    }

    @RequestMapping("test5")
    public String test5(AuthenticationTestForm form, Model model) {
        Map<String, Boolean> authMap = initAuthMap(AuthenticationTestForm.class);

        addKey(authMap, "button1");
        setViewTrueAll(authMap);
        setViewFalseAll(authMap);

        model.addAttribute("authMap", authMap);
        model.addAttribute("authMap", authMap);

        return "example/authenticationtest";

    }

}
