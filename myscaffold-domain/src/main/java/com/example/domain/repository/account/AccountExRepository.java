package com.example.domain.repository.account;

import com.example.domain.common.datatables.DataTablesInput;
import com.example.domain.model.Account;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface AccountExRepository {

    long countByExample(DataTablesInput input);
    List<Account> selectByExampleWithRowbounds(DataTablesInput input, RowBounds rowBounds);
    List<String> selectAllKey();

}
