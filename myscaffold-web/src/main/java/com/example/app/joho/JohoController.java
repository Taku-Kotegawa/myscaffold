package com.example.app.joho;

import com.example.domain.common.StateMap;
import com.example.domain.common.datatables.DataTablesInput;
import com.example.domain.common.datatables.DataTablesOutput;
import com.example.domain.model.TtJoho;
import com.example.domain.service.joho.JohoService;
import com.example.domain.service.userdetails.LoggedInUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@Controller
@RequestMapping("joho")
public class JohoController {

    @Autowired
    JohoService johoService;

    @ModelAttribute
    public JohoForm setUp() {
        return new JohoForm();
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("{seq}")
    public String view(Model model,
                       @PathVariable("seq") BigDecimal cdSeq,
                       @AuthenticationPrincipal LoggedInUser loggedInUser) {
        return "joho/view";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("list")
    public String list(Model model,
                       @AuthenticationPrincipal LoggedInUser loggedInUser) {
        return "joho/list";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping(value = "create", params = "form")
    public String createForm(Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser) {
        return "joho/form";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping(value = "create", params = "redo")
    public String createRedo(Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser) {
        return createRedo(model, loggedInUser);
    }


    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping(value = "{seq}/edit", params = "form")
    public String editForm(Model model,
                           @PathVariable("seq") BigDecimal cdSeq,
                           @AuthenticationPrincipal LoggedInUser loggedInUser) {
        return "joho/form";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping(value = "{seq}/edit", params = "redo")
    public String editRedo(Model model,
                           @PathVariable("seq") BigDecimal cdSeq,
                           @AuthenticationPrincipal LoggedInUser loggedInUser) {
        return editForm(model, cdSeq, loggedInUser);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("save")
    public String save(@Validated JohoForm form, BindingResult bindingResult,
                       Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {
        return "redirect:/joho/list";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @ResponseBody
    @GetMapping("list/json")
    public DataTablesOutput<JohoListRow> json(DataTablesInput input) {
        return new DataTablesOutput<>();
    }


    private StateMap getButtonStateMap(String operation, TtJoho record) {
        return null;
    }

    private StateMap getFiledStateMap(String operation, TtJoho record) {
        return null;
    }
}