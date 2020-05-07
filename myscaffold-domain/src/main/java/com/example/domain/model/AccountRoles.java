package com.example.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * アカウント・ロールエンティティ
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountRoles extends Account implements Serializable {

    /**
     * ロールラベル
     */
    private List<String> roleLables;

    /**
     * ロールラベル・ロール変換
     *
     * @return ロールのリスト
     */
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        if (roleLables != null) {
            for (String roleLabel : roleLables) {
                Role role = new Role();
                role.setUsername(this.getUsername());
                role.setRole(roleLabel);
                roles.add(role);
            }
        }
        return roles;
    }

}
