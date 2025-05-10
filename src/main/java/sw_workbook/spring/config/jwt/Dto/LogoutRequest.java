package sw_workbook.spring.config.jwt.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogoutRequest {
    private String username;
}
