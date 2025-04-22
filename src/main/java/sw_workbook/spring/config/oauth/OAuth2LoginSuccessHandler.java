package sw_workbook.spring.config.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sw_workbook.spring.Repository.MemberRepository;
import sw_workbook.spring.config.jwt.JwtGenerator;
import sw_workbook.spring.config.jwt.JwtToken;
import sw_workbook.spring.config.jwt.Repository.RefreshTokenRepository;
import sw_workbook.spring.config.jwt.entity.RefreshToken;
import sw_workbook.spring.domain.Member;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtGenerator jwtGenerator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        log.info("들어옴0.");

        // OAuth2 로그인한 사용자 정보 추출
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 정보 없음"));

        JwtToken jwtToken = jwtGenerator.generateToken(member, member.getRole());

        // RefreshToken 저장
        refreshTokenRepository.findByUsername(email)
                .ifPresentOrElse(
                        existing -> {
                            RefreshToken updated = existing.toBuilder()
                                    .refreshToken(jwtToken.getRefreshToken())
                                    .build();
                            refreshTokenRepository.save(updated);
                        },
                        () -> refreshTokenRepository.save(
                                RefreshToken.builder()
                                        .username(email)
                                        .refreshToken(jwtToken.getRefreshToken())
                                        .build())
                );

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", jwtToken.getRefreshToken())
                .httpOnly(true)
                .secure(false) // HTTPS 환경일 경우 true, 개발 중이면 false
                .path("/")
                .sameSite("Lax") // 프론트 분리 환경에서는 None (CORS 허용)
                .maxAge(7 * 24 * 60 * 60)
                .build();


        log.info("들어옴1.");
        response.addHeader("Set-Cookie", refreshCookie.toString());

        response.addHeader("Authorization","Bearer "+jwtToken.getAccessToken());
        //response.getWriter().write("로그인 성공");
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        // cors 정책에 따라 특정 헤더를 클라이언트에 접근가능하도록 허용 Authorization를 허용

        System.out.println("Oauth2 로그인 성공핸들러 실행됨");

        response.sendRedirect("/home");
    }
}