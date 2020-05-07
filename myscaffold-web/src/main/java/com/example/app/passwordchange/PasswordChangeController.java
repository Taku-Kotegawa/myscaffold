package com.example.app.passwordchange;

import com.example.domain.model.AccountRoles;
import com.example.domain.service.passwordchange.PasswordChangeService;
import com.example.domain.service.userdetails.LoggedInUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
@RequestMapping("password")
public class PasswordChangeController {

    @Inject
    PasswordChangeService passwordChangeService;

    @ModelAttribute("passwordChangeForm")
    public PasswordChangeForm setUpPasswordChangeForm() {
        return new PasswordChangeForm();
    }

    @GetMapping(params = "form")
    public String showForm(PasswordChangeForm form,
                           @AuthenticationPrincipal LoggedInUser userDetails, Model model) {

        AccountRoles accountRoles = userDetails.getAccountRoles();
        model.addAttribute("account", accountRoles);
        return "passwordchange/changeForm";
    }

    @PostMapping
    public String change(@AuthenticationPrincipal LoggedInUser userDetails,
                         @Validated PasswordChangeForm form,
                         BindingResult bindingResult, Model model) {

        AccountRoles accountRoles = userDetails.getAccountRoles();
        if (bindingResult.hasErrors() || !accountRoles.getUsername().equals(form.getUsername())) {
            model.addAttribute("account", accountRoles);
            return "passwordchange/changeForm";
        }

        passwordChangeService.updatePassword(form.getUsername(), form.getNewPassword());

        return "redirect:/password?complete";

    }

    @GetMapping(params = "complete")
    public String changeComplete() {
        return "passwordchange/changeComplete";
    }
}
