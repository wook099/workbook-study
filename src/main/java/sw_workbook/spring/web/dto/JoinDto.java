package sw_workbook.spring.web.dto;

import lombok.Getter;
import sw_workbook.spring.vaildation.annotation.ExistCategories;

import java.util.List;

@Getter
public class JoinDto {
    String name;
    Integer gender;
    Integer birthYear;
    Integer birthMonth;
    Integer birthDay;
    String address;
    String specAddress;

    @ExistCategories
    List<Long> preferCategory;
}
