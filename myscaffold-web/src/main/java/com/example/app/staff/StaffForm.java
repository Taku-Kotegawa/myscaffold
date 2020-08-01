package com.example.app.staff;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class StaffForm implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface Create {
    }

    public interface Update {
    }


    @NotNull(groups = {Update.class})
    private Long id;

    @NotNull(groups = {Update.class})
    private Long version;

    @NotNull
    private String staffNo;

    @NotNull
    private String name;

    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate birthday;

    private Boolean saveAsDraft;

}
