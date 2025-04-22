package sw_workbook.spring.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sw_workbook.spring.domain.Member;
import sw_workbook.spring.domain.enums.Role;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtGenerator {
    private final Key key;
    private static final String GRANT_TYPE = "Bearer";

    @Value("${spring.jwt.issuer}")
    private String jwtIssuer;

    @Value("${spring.jwt.access-token-expiration-millis}")
    private long accessTokenExpirationMillis;

    @Value("${spring.jwt.refresh-token-expiration-millis}")
    private long refreshTokenExpirationMillis;

    public JwtGenerator(@Value("${spring.jwt.secret}") String secretKey) {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(Member member, Role roles) {

        long now = (new Date()).getTime();

        String authorities = roles.getValue();

        String accessToken = Jwts.builder()
                .setIssuer(jwtIssuer)
                //.setSubject(String.valueOf(userId))//userid로 할경우 jwt는 사양상 String 타입을 요구함
                .setSubject(member.getEmail())
                .claim("auth", authorities)// 권한 설정
                .setExpiration(new Date(now+accessTokenExpirationMillis))
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();// 해더 페이로드 시그니처를 .으로 만든 문자열 인코딩&조합

        String refreshToken = Jwts.builder()
                .setSubject(member.getEmail())
                .setExpiration(new Date(now + refreshTokenExpirationMillis))//7일 만료
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType(GRANT_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    public String createAccessToken(String email, String role) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setIssuer(jwtIssuer)
                .setSubject(email)
                .claim("auth", role)
                .setExpiration(new Date(now + accessTokenExpirationMillis))
                .setIssuedAt(new Date(now))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
