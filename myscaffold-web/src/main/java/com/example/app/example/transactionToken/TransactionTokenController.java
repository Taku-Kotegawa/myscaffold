package com.example.app.example.transactionToken;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@SessionAttributes(types = {TransactionTokenForm.class})
@RequestMapping("/transactiontoken")
@TransactionTokenCheck("transactiontoken")
public class TransactionTokenController {

    @GetMapping()
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String createForm(TransactionTokenForm form, Model model, HttpServletRequest request) {
        form.setScreenId(1);
        return "example/transactiontokenform";
    }

    @PostMapping()
    @TransactionTokenCheck
    public String create(@Validated TransactionTokenForm form, BindingResult bindingResult, Model model) {
        form.setScreenId(2);
        return "example/transactiontokenform";
    }

    @PostMapping(params = "a")
    @TransactionTokenCheck
    public String createA(@Validated TransactionTokenForm form, BindingResult bindingResult, Model model) {
        form.setScreenId(3);
        return "example/transactiontokenform";
    }

    @PostMapping(params = "b")
    @TransactionTokenCheck
    public String createb(@Validated TransactionTokenForm form, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
        form.setScreenId(4);
        return "example/transactiontokenform";
    }

    @PostMapping(params = "c")
    public String createc(@Validated TransactionTokenForm form, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
        return "redirect:/transactiontoken";
    }
}
