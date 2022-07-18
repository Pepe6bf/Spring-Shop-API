package shop.spring.dev.springshop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import shop.spring.dev.springshop.jwt.JwtAccessDeniedHandler;
import shop.spring.dev.springshop.jwt.JwtAuthenticationEntryPoint;
import shop.spring.dev.springshop.jwt.JwtSecurityConfig;
import shop.spring.dev.springshop.jwt.TokenProvider;

@Configuration
// PreAuthorize 어노테이션을 메소드 단위로 추가하기 위해서 적용
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

    // 토큰의 생성과 검증을 담당
    private final TokenProvider tokenProvider;
    // 401 에러의 리턴을 담당
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    // 403 에러의 리턴을 담당
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // 의존성 주입
    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler) {

        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 파비콘은 무시하는 것으로 설정
        return (web) -> web.ignoring()
                .antMatchers("/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // token을 사용하는 방식이기 때문에 csrf를 disable한다.
        http.csrf().disable();

        // 401, 403 예외 핸들러를 우리가 제작한 핸들러로 넣어줌
        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);

        // 세션을 사용하지 않기 때문에, 세션 설정을 STATELESS로 설정
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 요청 설정
        http.authorizeRequests()
                // 토큰을 받기위한 로그인 api
                .antMatchers("/login").permitAll()
                // 회원 가입을 위한 api
                .antMatchers("/signup", "/signup-admin").permitAll()
                // /admin으로 시작하는 요청은 관리자만 접근 가능
                .antMatchers("/admin/**").hasRole("ADMIN")
                // 나머지는 모두 인증 필요
                .anyRequest().authenticated();

        // 우리가 만든 JWT설정 파일을 추가
        http.apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }

}
