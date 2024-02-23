package org.had.accountservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorHPR {
    @NotNull(message = "hprId should not be empty, check field name")
    private String hprId;

    @NotNull(message = "password should not be empty, check field name")
    private String password;
}
