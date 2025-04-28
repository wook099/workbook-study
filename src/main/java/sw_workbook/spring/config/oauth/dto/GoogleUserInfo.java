package sw_workbook.spring.config.oauth.dto;

import sw_workbook.spring.config.oauth.dto.OAuth2UserInfo;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getEmail() {
        if (attributes.get("email") != null) {
            return (String) attributes.get("email");
        }
        return null; // 여기선 그냥 null 주고
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
