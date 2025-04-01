package sw_workbook.spring.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import sw_workbook.spring.Repository.MemberRepository;
import sw_workbook.spring.config.jwt.JwtGenerator;
import sw_workbook.spring.config.jwt.JwtToken;
import sw_workbook.spring.domain.Member;
import sw_workbook.spring.domain.enums.Gender;
import sw_workbook.spring.domain.enums.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {//DefaultOAuth2UserService<< OAuth2 로그인시 사용자 정보 가져옴
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator; // JWT 발급기 추가
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        OAuth2User oAuth2User = super.loadUser(userRequest);//loadUser(userRequest): OAuth2 로그인 요청이 들어오면 실행되는 메서드입니다.
        //카톡에서 제공하는 사용자 정보 오어스2 객체로 받아옴

        Map<String, Object> attributes = oAuth2User.getAttributes();//attribute는 사용자의 기본정보 포함
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        //Auth2 로그인에서 반환된 사용자 정보 중 특정 서브 정보를 추출, properties라는 속성 내에 추가적인 사용자 정보를 저장

        String nickname = (String) properties.get("nickname");
        String email = nickname + "@kakao.com"; // 임시 이메일 생성

        // 사용자 정보 저장 또는 업데이트
        Member member = saveOrUpdateUser(email, nickname);

        JwtToken jwtToken = jwtGenerator.generateToken(member.getId(), member.getRole());

        log.info("Generated Access Token: {}", jwtToken.getAccessToken());


        // 이메일을 Principal로 사용하기 위해 attributes 수정
        Map<String, Object> modifiedAttributes = new HashMap<>(attributes);
        modifiedAttributes.put("email", email);
        modifiedAttributes.put("token", jwtToken.getAccessToken());
        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                modifiedAttributes,
                "email"  // email Principal로 설정
        );
    }

    private Member saveOrUpdateUser(String email, String nickname) {
        Member member = memberRepository.findByEmail(email)
                .orElse(Member.builder()
                        .email(email)
                        .name(nickname)
                        .password(passwordEncoder.encode("OAUTH_USER_" + UUID.randomUUID()))
                        .gender(Gender.NONE)  // 기본값 설정
                        .address("소셜로그인")  // 기본값 설정
                        .specAddress("소셜로그인")  // 기본값 설정
                        .role(Role.USER)
                        .build());

        return memberRepository.save(member);
    }



}
