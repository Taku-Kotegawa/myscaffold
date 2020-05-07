package com.example.app.passwordreissue;

import com.example.app.common.validation.NotReusedPassword;
import com.example.app.common.validation.StrongPassword;
import lombok.Data;
import org.terasoluna.gfw.common.validator.constraints.Compare;

import java.io.Serializable;

@Data
@Compare(left = "newPassword", right = "confirmNewPassword", operator = Compare.Operator.EQUAL)
@StrongPassword(usernamePropertyName = "username", newPasswordPropertyName = "newPassword")
@NotReusedPassword(usernamePropertyName = "username", newPasswordPropertyName = "newPassword")
public class PasswordResetForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String username;

    /**
     *
     */
    private String token;

    /**
     *
     */
    private String secret;

    /**
     *
     */
    private String newPassword;

    /**
     *
     */
    private String confirmNewPassword;

}
