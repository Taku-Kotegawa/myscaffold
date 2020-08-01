package com.example.domain.service.staff;

import com.example.domain.example.*;
import com.example.domain.service.userdetails.LoggedInUser;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface StaffService {


    Staff findOne(String staff_no);

    Staff findOne(long id);

    Staff create(Staff record, Boolean asDraft);

    Staff save(Staff record, Boolean asDraft);

    Staff save(Staff record, Boolean asDraft, Boolean checkChange);

    Staff invalid(Long id);

    Staff cancelDraft(long id);

    void delete(String staff_no);

    void delete(long id);

    void deleteWithHistory(long id);

    Boolean exists(String staff_no);

    Boolean exists(long id);

    List<Staff> findByExample(StaffExample example);

    List<Staff> findByExample(StaffExample example, RowBounds rowBounds);

    List<StaffRev> findRevByExample(StaffRevExample example);

    List<StaffRev> findRevByExample(StaffRevExample example, RowBounds rowBounds);

    List<StaffView> findViewByExample(StaffViewExample example);

    List<StaffView> findViewByExample(StaffViewExample example, RowBounds rowBounds);

    Boolean hasAuthority(String Operation, LoggedInUser loggedInUser);

}
