package sw_workbook.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sw_workbook.spring.config.jwt.JwtAuthenticationFilter;
import sw_workbook.spring.config.jwt.JwtProvider;
import sw_workbook.spring.config.oauth.CustomAuthenticationEntryPoint;
import sw_workbook.spring.config.oauth.OAuth2LoginSuccessHandler;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))//세션비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home","/login", "login/oauth2/**","/signup","/oauth2/**", "/members/signup", "api/hello","/css/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()) // 그 외 모든 요청은 인증 필요
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
//                        .defaultSuccessUrl("/home",true)
                        .successHandler(oAuth2LoginSuccessHandler) //
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint))
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); // 쿠키 허용
        configuration.addAllowedOrigin("http://localhost:5173"); // 허용할 프론트엔드 URL
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}