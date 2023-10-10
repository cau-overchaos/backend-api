package algogather.api.config;

import algogather.api.config.security.custom.CustomAuthenticationEntryPoint;
import algogather.api.config.security.custom.CustomLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static algogather.api.config.FilterPath.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Bean
    @Profile("dev")
    SecurityFilterChain securityFilterChainDevelopment(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(developmentWhiteList).permitAll()
                .antMatchers(POST, deploymentWhiteListPOST).permitAll() // POST 요청 하용
                .antMatchers(GET, deploymentWhiteListGET).permitAll() // GET 요청 허용
                .antMatchers(swaggerPath).permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();

        http.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);

        http
                .logout() // 로그아웃 기능 작동함
                .logoutUrl("/logout") // 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .deleteCookies("JSESSIONID"); // 로그아웃 후 쿠키 삭제

        return http.build();
    }

    @Bean
    @Profile("prod")
    SecurityFilterChain securityFilterChainDeploy(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(POST, deploymentWhiteListPOST).permitAll() // POST 요청 하용
                .antMatchers(GET, deploymentWhiteListPOST).permitAll() // GET 요청 허용
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);

        http
                .logout() // 로그아웃 기능 작동함
                .logoutUrl("/logout") // 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .deleteCookies("JSESSIONID"); // 로그아웃 후 쿠키 삭제

        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}


