package com.example.app.staff;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Accessors(chain = true)
public class StaffForm implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull(groups = {Update.class, Delete.class})
    private Long id;
    @NotNull(groups = {Update.class})
    private Long version;
    @NotNull
    private Integer status;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String createdBy;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private String updatedBy;
    @NotNull
    private String staffNo;
    @NotNull
    private String name;
    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate birthday;

    public interface Create {
    }

    public interface Update {
    }

    public interface Delete {
    }

}
