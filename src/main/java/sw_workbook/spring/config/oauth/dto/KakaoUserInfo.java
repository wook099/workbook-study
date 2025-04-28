package sw_workbook.spring.config.oauth.dto;

import sw_workbook.spring.config.oauth.dto.OAuth2UserInfo;

import java.util.Map;
import java.util.UUID;

public class KakaoUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");//카카오 계좌정보가져옴(이메일, 프로필정보)
        if (kakaoAccount != null && kakaoAccount.get("email") != null) {
            return (String) kakaoAccount.get("email");
        }
        return "kakao_" + UUID.randomUUID() + "@social.com"; // 여기서 생성
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        if (properties != null && properties.get("nickname") != null) {
            return (String) properties.get("nickname");
        }
        return "카카오사용자"; // 닉네임 없으면 기본값
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}

