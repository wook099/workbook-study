package sw_workbook.spring.domain.enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String value;

    Role(String value){
        this.value = value;
    }

}
