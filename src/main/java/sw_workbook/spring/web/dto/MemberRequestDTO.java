package sw_workbook.spring.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import sw_workbook.spring.domain.enums.Role;
import sw_workbook.spring.vaildation.annotation.ExistCategories;

import java.util.List;

public class MemberRequestDTO {

    @Getter
    @Setter// tymeleaf에서 사용하기 위해 추가함
    public static class JoinDto{
        @NotBlank
        String name;

        @Email
        String email;
        @NotBlank
        String password;
        @NotNull
        Role role;

        @NotNull
        Integer gender;
        @NotNull
        Integer birthYear;
        @NotNull
        Integer birthMonth;
        @NotNull
        Integer birthDay;
        @Size(min = 5, max = 12)
        String address;
        @Size(min = 5, max = 12)
        String specAddress;
        @ExistCategories
        List<Long> preferCategory;
    }
}