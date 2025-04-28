package sw_workbook.spring.config.oauth.dto;

import sw_workbook.spring.config.oauth.dto.OAuth2UserInfo;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;


    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("email");  // response 안에서 email 꺼내기
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("nickname");  // response 안에서 nickname 꺼내기
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
