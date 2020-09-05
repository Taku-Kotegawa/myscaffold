package com.example.domain.service.joho;

import com.example.domain.common.datatables.DataTablesInput;
import com.example.domain.model.TtJoho;

import java.util.List;

public interface JohoService {

    List<TtJoho> findAllByInput(DataTablesInput input);

    TtJoho save(TtJoho entity);

    TtJoho findOne(String cd_seq);

}
