package sw_workbook.spring.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sw_workbook.spring.domain.enums.Role;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtGenerator {
    private final Key key;
    private static final String GRANT_TYPE = "Bearer";

    @Value("${spring.jwt.access-token-expiration-millis}")
    private long accessTokenExpirationMillis;

    @Value("${spring.jwt.refresh-token-expiration-millis}")
    private long refreshTokenExpirationMillis;

    public JwtGenerator(@Value("${spring.jwt.secret}") String secretKey) {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(Long userId, Role roles) {

        long now = (new Date()).getTime();

        String authorities = roles.getValue();

        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("auth", authorities)// 권한 설정
                .setExpiration(new Date(now+accessTokenExpirationMillis))
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(String.valueOf(userId))
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
}
