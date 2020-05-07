package com.example.app.passwordchange;

import com.example.app.common.validation.ConfirmOldPassword;
import com.example.app.common.validation.NotReusedPassword;
import com.example.app.common.validation.StrongPassword;
import lombok.Data;
import org.terasoluna.gfw.common.validator.constraints.Compare;

import java.io.Serializable;

@Data
@Compare(left = "newPassword", right = "confirmNewPassword", operator = Compare.Operator.EQUAL)
@StrongPassword(usernamePropertyName = "username", newPasswordPropertyName = "newPassword")
@NotReusedPassword(usernamePropertyName = "username", newPasswordPropertyName = "newPassword")
@ConfirmOldPassword(usernamePropertyName = "username", oldPasswordPropertyName = "oldPassword")
public class PasswordChangeForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String oldPassword;

    private String newPassword;

    private String confirmNewPassword;


}
