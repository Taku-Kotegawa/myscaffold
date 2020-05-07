package com.example.app.account;

import com.example.app.account.AccountCreateForm.Confirm;
import com.example.app.account.AccountCreateForm.CreateAccount;
import com.example.domain.common.message.MessageKeys;
import com.example.domain.model.AccountImage;
import com.example.domain.model.AccountRoles;
import com.example.domain.model.TempFile;
import com.example.domain.service.account.AccountSharedService;
import com.example.domain.service.fileupload.FileUploadSharedService;
import com.example.domain.service.userdetails.LoggedInUser;
import com.github.dozermapper.core.Mapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.inject.Inject;
import javax.validation.groups.Default;
import java.io.IOException;
import java.util.Arrays;

@Controller
@RequestMapping("accounts")
public class AccountController {


    @Inject
    FileUploadSharedService fileUploadSharedService;

    @Inject
    AccountSharedService accountSharedService;

    @Inject
    Mapper beanMapper;

    @ModelAttribute
    public AccountCreateForm SetUpAccountCreateForm() {
        return new AccountCreateForm();
    }

    @GetMapping
    public String view(@AuthenticationPrincipal LoggedInUser userDetails,
                       Model model) {
        AccountRoles accountRoles = userDetails.getAccountRoles();
        model.addAttribute("account", accountRoles);
        return "account/view";
    }

    @GetMapping("/image")
    @ResponseBody
    public ResponseEntity<byte[]> showImage(
            @AuthenticationPrincipal LoggedInUser userDetails)
            throws IOException {
        AccountImage userImage = accountSharedService.getImage(userDetails
                .getUsername());
        HttpHeaders headers = new HttpHeaders();
        if (userImage.getExtension().equalsIgnoreCase("png")) {
            headers.setContentType(MediaType.IMAGE_PNG);
        } else if (userImage.getExtension().equalsIgnoreCase("gif")) {
            headers.setContentType(MediaType.IMAGE_GIF);
        } else if (userImage.getExtension().equalsIgnoreCase("jpg")) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }
        return new ResponseEntity<byte[]>(userImage.getBody(), headers,
                HttpStatus.OK);
    }

    @GetMapping(value = "/create", params = "form")
    public String createForm() {
        return "account/accountCreateForm";
    }

    @PostMapping(value = "/create", params = "redo")
    public String redoCreateForm(AccountCreateForm form) {
        return "account/accountCreateForm";
    }

    @PostMapping(value = "/create", params = "confirm")
    public String createConfirm(
            @Validated({Confirm.class, Default.class}) AccountCreateForm form,
            BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return createForm();
        }
        if (accountSharedService.exists(form.getUsername())) {
            model.addAttribute(ResultMessages.error().add(
                    MessageKeys.E_SL_AC_5001));
            return createForm();
        }
        try {
            TempFile tempFile = new TempFile();
            tempFile.setBody(form.getImage().getBytes());
            tempFile.setOriginalName(form.getImage().getOriginalFilename());
            String fileId = fileUploadSharedService.uploadTempFile(tempFile);
            form.setImageId(fileId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        redirectAttributes.addFlashAttribute("accountCreateForm", form);
        return "account/accountConfirm";
    }

    @PostMapping("/create")
    public String create(
            @Validated({CreateAccount.class, Default.class}) AccountCreateForm form,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return createForm();
        }
        AccountRoles accountRoles = beanMapper.map(form, AccountRoles.class);
        accountRoles.setRoleLables(Arrays.asList("USER"));

        String password = accountSharedService.create(accountRoles,
                form.getImageId());
        redirectAttributes.addFlashAttribute("firstName", form.getFirstName());
        redirectAttributes.addFlashAttribute("lastName", form.getLastName());
        redirectAttributes.addFlashAttribute("password", password);
        return "redirect:/accounts/create?complete";
    }

    @GetMapping(value = "/create", params = "complete")
    public String createComplete() {
        return "account/createComplete";
    }


}
