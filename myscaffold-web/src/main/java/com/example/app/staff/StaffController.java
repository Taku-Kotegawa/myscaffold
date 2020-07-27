package com.example.app.staff;

import com.example.domain.common.Constants;
import com.example.domain.service.staff.StaffService;
import com.example.domain.service.userdetails.LoggedInUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.groups.Default;

import static com.example.app.staff.StaffForm.Create;
import static com.example.app.staff.StaffForm.Update;

@Slf4j
@Controller
@RequestMapping("staff")
public class StaffController {

    @Autowired
    StaffService staffService;

    /*
     * ハンドラーメソッドの引数
     * @Validated StaffForm form, BindingResult bindingResult　-> create/update
     * StaffForm form -> Redoの時
     * @PathVariable("id") String id -> URLにIDを含む時
     * RedirectAttribute attribute -> リダイレクトするとき
     * Model model -> 常
     * @AuthenticationPrincipal LoggedInUser loggedInUser -> 常
     * @RequestParam(name="destination", required = false) String destination, -> list/view以外常
     *
     */


    @ModelAttribute
    private StaffForm setUp() {
        return StaffForm.builder().build();
    }

    // ---------------- 一覧 -----------------------------------------------------

    /**
     * 一覧画面の表示
     *
     * @param model
     * @param loggedInUser
     * @return
     */
    @GetMapping(value = "list")
    public String list(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        staffService.hasAuthority(loggedInUser, Constants.OPERATION.LIST);

        return "staff/list";
    }


    // ---------------- 新規登録 -----------------------------------------------------

    /**
     * 新規作成画面を開く
     */
    @GetMapping(value = "create", params = "form")
    public String createForm(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(name = "copy", required = false) String copy,
                             @RequestParam(name = "destination", required = false) String destination) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        staffService.hasAuthority(loggedInUser, Constants.OPERATION.CREATE);

        model.addAttribute("destination", destination);

        return "staff/createUpdateForm";
    }

    /**
     * 新規作成画面を再表示
     */
    @PostMapping(value = "create", params = "redo")
    public String createRedo(StaffForm form, Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(name = "destination", required = false) String destination) {
        return "staff/createUpdateForm";
    }

    /**
     * 新規登録
     */
    @PostMapping(value = "create")
    public String createRedo(@Validated({Create.class, Default.class}) StaffForm form, BindingResult bindingResult, Model model,
                             RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(name = "destination", required = false) String destination) {
        if (bindingResult.hasErrors()) {
            return createRedo(form, model, loggedInUser, destination);
        }
        return "redirect:" + form.getId() + "/update?form";
    }

    // ---------------- 編集 ---------------------------------------------------------

    /**
     * 編集画面を開く
     */
    @GetMapping(value = "{id}/update", params = "form")
    public String updateForm(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") String id,
                             @RequestParam(name = "destination", required = false) String destination) {
        return "staff/createUpdateForm";
    }

    /**
     * 編集画面の再表示
     */
    @PostMapping(value = "{id}/update", params = "redo")
    public String updateRedo(StaffForm form, Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") String id,
                             @RequestParam(name = "destination", required = false) String destination) {
        return "staff/createUpdateForm";
    }

    /**
     * 更新
     */
    @PostMapping(value = "{id}/update")
    public String update(@Validated({Update.class, Default.class}) StaffForm form, BindingResult bindingResult,
                         Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") String id,
                         @RequestParam(name = "destination", required = false) String destination) {
        return "redirect:{id}/update?form";
    }

    // ---------------- 削除 ---------------------------------------------------------

    /**
     * 削除
     */
    @GetMapping(value = "{id}/delete")
    public String delete(Model model, RedirectAttributes attributes, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") String id,
                         @RequestParam(name = "destination", required = false) String destination) {
        return "redirect:list";
    }

    // ---------------- 参照 ---------------------------------------------------------

    /**
     * 参照画面の表示
     */
    @GetMapping(value = "{id}")
    public String detail(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @RequestParam(name = "destination", required = false) String destination) {
        return "staff/detail";
    }


}