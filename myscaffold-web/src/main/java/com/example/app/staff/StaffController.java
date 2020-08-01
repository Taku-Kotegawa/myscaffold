package com.example.app.staff;

import com.example.domain.common.Constants;
import com.example.domain.common.StateMap;
import com.example.domain.example.Staff;
import com.example.domain.example.StaffExample;
import com.example.domain.service.staff.StaffService;
import com.example.domain.service.userdetails.LoggedInUser;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.staff.StaffForm.Create;
import static com.example.app.staff.StaffForm.Update;
import static com.example.domain.common.Constants.BUTTON;
import static com.example.domain.common.Constants.OPERATION;

@Slf4j
@Controller
@RequestMapping("staff")
@TransactionTokenCheck("staff")
public class StaffController {

    @Autowired
    StaffService staffService;

    @Autowired
    Mapper beanMapper;

    /*
     * ハンドラーメソッドの引数
     * @Validated StaffForm form, BindingResult bindingResult　-> create/update
     * StaffForm form -> Redoの時
     * RedirectAttribute attribute -> リダイレクトするとき
     * Model model -> 常
     * @AuthenticationPrincipal LoggedInUser loggedInUser -> 常
     * @PathVariable("id") String id -> URLにIDを含む時
     * @RequestParam(name="destination", required = false) String destination, -> list/view以外常
     *
     */

    @ModelAttribute
    private StaffForm setUp() {

        return new StaffForm();
    }

    private StateMap getButtonStateMap(String operation, Staff record) {

        if (record == null) {
            record = new Staff();
        }

        List<String> includeKeys = new ArrayList<>();
        includeKeys.add(BUTTON.GOTOLIST);
        includeKeys.add(BUTTON.GOTOUPDATE);
        includeKeys.add(BUTTON.VIEW);
        includeKeys.add(BUTTON.SAVE_AS_DRAFT);
        includeKeys.add(BUTTON.CANCEL_DARFT);
        includeKeys.add(BUTTON.SAVE);
        includeKeys.add(BUTTON.INVALID);
        includeKeys.add(BUTTON.DELETE);
        StateMap stateMap = new StateMap(Default.class, includeKeys, new ArrayList<>());

        // 常に表示
        stateMap.setViewTrue(BUTTON.GOTOLIST);

        // 新規作成
        if (OPERATION.CREATE.equals(operation)) {
            stateMap.setViewTrue(BUTTON.SAVE_AS_DRAFT);
            stateMap.setViewTrue(BUTTON.SAVE);
        }

        // 編集
        if (OPERATION.UPDATE.equals(operation)) {

            // ステータスが下書き以外の場合
            if (!Constants.STATUS.DRAFT.equals(record.getStatus())) {
                stateMap.setDisabledTrue(BUTTON.CANCEL_DARFT);
            }

            if (Constants.STATUS.DRAFT.equals(record.getStatus())) {
                stateMap.setViewTrue(BUTTON.SAVE_AS_DRAFT);
                stateMap.setViewTrue(BUTTON.SAVE);
                stateMap.setViewTrue(BUTTON.CANCEL_DARFT);
                stateMap.setViewTrue(BUTTON.VIEW);
            }

            if (Constants.STATUS.VALID.equals(record.getStatus())) {
                stateMap.setViewTrue(BUTTON.SAVE_AS_DRAFT);
                stateMap.setViewTrue(BUTTON.SAVE);
                stateMap.setViewTrue(BUTTON.CANCEL_DARFT);
                stateMap.setViewTrue(BUTTON.VIEW);
            }

            if (Constants.STATUS.INVALID.equals(record.getStatus())) {
                stateMap.setViewTrue(BUTTON.VIEW);
                stateMap.setViewTrue(BUTTON.DELETE);
            }

        }

        // 参照
        if (OPERATION.VIEW.equals(operation)) {
            if (Constants.STATUS.DRAFT.equals(record.getStatus())) {
                stateMap.setViewTrue(BUTTON.GOTOUPDATE);
            }

            // スタータスが公開時
            if (Constants.STATUS.VALID.equals(record.getStatus())) {
                stateMap.setViewTrue(BUTTON.GOTOUPDATE);
                stateMap.setViewTrue(BUTTON.INVALID);
                stateMap.setViewTrue(BUTTON.DELETE);
            }

            // スタータスが無効
            if (Constants.STATUS.INVALID.equals(record.getStatus())) {
                stateMap.setViewTrue(BUTTON.DELETE);
            }
        }

        return stateMap;
    }


    private StateMap getFiledStateMap(String operation, Staff record) {
        List<String> excludeKeys = new ArrayList<>();

        // 常設の隠しフィールドは状態管理しない
        excludeKeys.add("id");
        excludeKeys.add("version");

        StateMap stateMap = new StateMap(StaffForm.class, new ArrayList<>(), excludeKeys);

        // 新規作成
        if (OPERATION.CREATE.equals(operation)) {
            stateMap.setInputTrueAll();
        }

        // 編集
        if (OPERATION.UPDATE.equals(operation)) {
            stateMap.setInputTrueAll();
            stateMap.setReadOnlyTrue("staffNo");

            // スタータスが無効
            if (Constants.STATUS.INVALID.equals(record.getStatus())) {
                stateMap.setReadOnlyTrueAll();
            }
        }

        // 参照
        if (OPERATION.VIEW.equals(operation)) {
            stateMap.setViewTrueAll();
        }

        return stateMap;
    }

    // ---------------- 一覧 -----------------------------------------------------

    @GetMapping()
    public String init(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {
        return list(model, loggedInUser);
    }

    /**
     * 一覧画面の表示
     */
    @GetMapping(value = "list")
    public String list(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        staffService.hasAuthority(OPERATION.LIST, loggedInUser);

        List<Staff> staffList = staffService.findByExample(new StaffExample());

        model.addAttribute("staffList", staffList);

        return "staff/list";
    }

    // ---------------- 新規登録 -----------------------------------------------------

    /**
     * 新規作成画面を開く
     */
    @GetMapping(value = "create", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String createForm(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(name = "copy", required = false) String copy,
                             @RequestParam(name = "destination", required = false) String destination) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        staffService.hasAuthority(OPERATION.CREATE, loggedInUser);

        // ボタンの状態を設定
        StateMap buttonState = getButtonStateMap(OPERATION.CREATE, null);
        model.addAttribute("buttonState", buttonState.asMap());

        // フィールドの状態を設定
        StateMap filedState = getFiledStateMap(OPERATION.CREATE, null);
        model.addAttribute("fieldState", filedState.asMap());

        // destination
        model.addAttribute("destination", destination);

        return "staff/form";
    }

    /**
     * 新規作成画面を再表示
     */
    @PostMapping(value = "create", params = "redo")
    @TransactionTokenCheck
    public String createRedo(StaffForm form, Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(name = "destination", required = false) String destination) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        staffService.hasAuthority(OPERATION.CREATE, loggedInUser);

        // ボタンの状態を設定
        StateMap buttonState = getButtonStateMap(OPERATION.CREATE, null);
        model.addAttribute("buttonState", buttonState.asMap());

        // フィールドの状態を設定
        StateMap filedState = getFiledStateMap(OPERATION.CREATE, null);
        model.addAttribute("fieldState", filedState.asMap());

        return "staff/form";
    }

    /**
     * 新規登録
     */
    @PostMapping(value = "create")
    @TransactionTokenCheck
    public String createRedo(@Validated({Create.class, Default.class}) StaffForm form, BindingResult bindingResult, Model model,
                             RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(name = "destination", required = false) String destination) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        staffService.hasAuthority(OPERATION.CREATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return createRedo(form, model, loggedInUser, destination);
        }

        Staff staff = beanMapper.map(form, Staff.class);

        staffService.create(staff, form.getSaveAsDraft());

        return "redirect:" + staff.getId() + "/update?form";
    }

    // ---------------- 編集 ---------------------------------------------------------

    /**
     * 編集画面を開く
     */
    @GetMapping(value = "{id}/update", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String updateForm(StaffForm form, Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") Long id,
                             @RequestParam(name = "destination", required = false) String destination) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        staffService.hasAuthority(OPERATION.UPDATE, loggedInUser);

        Staff staff = staffService.findOne(id);
        beanMapper.map(staff, form);
        model.addAttribute("staff", staff);

        // ボタンの状態を設定
        StateMap buttonState = getButtonStateMap(OPERATION.UPDATE, staff);
        model.addAttribute("buttonState", buttonState.asMap());

        // フィールドの状態を設定
        StateMap filedState = getFiledStateMap(OPERATION.UPDATE, staff);
        model.addAttribute("fieldState", filedState.asMap());

        // destination
        model.addAttribute("destination", destination);

        return "staff/form";
    }

    /**
     * 編集画面の再表示
     */
    @PostMapping(value = "{id}/update", params = "redo")
    @TransactionTokenCheck
    public String updateRedo(StaffForm form, Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") Long id,
                             @RequestParam(name = "destination", required = false) String destination) {

        return updateForm(form, model, loggedInUser, id, destination);
    }

    /**
     * 更新
     */
    @PostMapping(value = "{id}/update")
    @TransactionTokenCheck
    public String update(@Validated({Update.class, Default.class}) StaffForm form, BindingResult bindingResult,
                         Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id,
                         @RequestParam(name = "destination", required = false) String destination) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        staffService.hasAuthority(OPERATION.UPDATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return updateRedo(form, model, loggedInUser, id, destination);
        }

        Staff staff = beanMapper.map(form, Staff.class);
        staffService.save(staff, form.getSaveAsDraft());

        return "redirect:/staff/{id}/update?form";
    }

    // ---------------- 下書きの取消 ---------------------------------------------------------

    @PostMapping(value = "{id}/update", params = "cancelDraft")
    @TransactionTokenCheck
    public String cancelDraft(StaffForm form, BindingResult bindingResult,
                              Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                              @PathVariable("id") Long id,
                              @RequestParam(name = "destination", required = false) String destination) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        staffService.hasAuthority(OPERATION.UPDATE, loggedInUser);

        if (staffService.cancelDraft(form.getId()) != null) {
            return "redirect:/staff/{id}/update?form";
        } else {
            return "redirect:/staff/list";
        }

    }

    // ---------------- 削除 ---------------------------------------------------------

    /**
     * 削除
     */
    @GetMapping(value = "{id}/delete")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String delete(Model model, RedirectAttributes attributes, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id,
                         @RequestParam(name = "destination", required = false) String destination) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        staffService.hasAuthority(OPERATION.DELETE, loggedInUser);

        staffService.deleteWithHistory(id);

        return "redirect:/staff/list";
    }

    // ---------------- 無効化 ---------------------------------------------------------

    @GetMapping(value = "{id}/invalid")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String invalid(Model model, RedirectAttributes attributes, @AuthenticationPrincipal LoggedInUser loggedInUser,
                          @PathVariable("id") Long id,
                          @RequestParam(name = "destination", required = false) String destination) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        staffService.hasAuthority(OPERATION.INVALID, loggedInUser);

        staffService.invalid(id);

        return "redirect:/staff/{id}";
    }

    // ---------------- 参照 ---------------------------------------------------------

    /**
     * 参照画面の表示
     */
    @GetMapping(value = "{id}")
    public String view(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id,
                         @RequestParam(name = "destination", required = false) String destination) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        staffService.hasAuthority(OPERATION.VIEW, loggedInUser);

        Staff staff = staffService.findOne(id);
        model.addAttribute("staff", staff);

        // ボタンの状態を設定
        StateMap buttonState = getButtonStateMap(OPERATION.VIEW, staff);
        model.addAttribute("buttonState", buttonState.asMap());

        // フィールドの状態を設定
        StateMap filedState = getFiledStateMap(OPERATION.VIEW, staff);
        model.addAttribute("fieldState", filedState.asMap());

        // destination
        model.addAttribute("destination", destination);

        return "staff/form";
    }
}