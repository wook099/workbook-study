package sw_workbook.spring.config.oauth.dto;

import java.util.Map;

public interface OAuth2UserInfo {
    String getEmail();
    String getName();
    Map<String, Object> getAttributes();
}
