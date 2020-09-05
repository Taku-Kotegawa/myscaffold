package com.example.domain.service.joho;

import com.example.domain.common.datatables.DataTablesInput;
import com.example.domain.model.TtJoho;
import com.example.domain.repository.asg.TtJohoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JohoServiceImpl implements JohoService {

    @Autowired
    TtJohoRepository ttJohoRepository;

    @Override
    public List<TtJoho> findAllByInput(DataTablesInput input) {
        return null;
    }

    @Override
    public TtJoho save(TtJoho entity) {
        return null;
    }

    @Override
    public TtJoho findOne(String cd_seq) {
        return null;
    }
}
