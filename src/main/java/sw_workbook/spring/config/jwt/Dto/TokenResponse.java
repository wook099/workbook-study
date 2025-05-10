package sw_workbook.spring.config.jwt.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
}
