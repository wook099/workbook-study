package sw_workbook.spring.config.oauth.dto;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        switch (registrationId) {
            case "google": return new GoogleUserInfo(attributes);
            case "kakao": return new KakaoUserInfo(attributes);
            case "naver": return new NaverUserInfo(attributes);
            default: throw new IllegalArgumentException("지원하지 않는 소셜 로그인입니다: " + registrationId);
        }
    }
}
