/*
 * Copyright(c) 2013 NTT DATA Corporation. Copyright(c) 2013 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.example.domain.service.userdetails;

import com.example.domain.model.Account;
import com.example.domain.model.AccountRoles;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;

public class LoggedInUser extends User {

    private static final long serialVersionUID = 1L;

    private final AccountRoles accountRoles;

    private final LocalDateTime lastLoginDate;

    public LoggedInUser(AccountRoles accountRoles, boolean isLocked,
                        LocalDateTime lastLoginDate,
                        List<SimpleGrantedAuthority> authorities) {
        super(accountRoles.getUsername(), accountRoles.getPassword(),
                true, true, true,
                !isLocked, authorities);
        this.accountRoles = accountRoles;
        this.lastLoginDate = lastLoginDate;
    }

    public Account getAccount() {
        return (Account) accountRoles;
    }

    public AccountRoles getAccountRoles() {
        return accountRoles;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

}
