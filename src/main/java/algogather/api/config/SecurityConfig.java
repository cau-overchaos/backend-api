package algogather.api.config;

import algogather.api.config.security.custom.CustomAuthenticationEntryPoint;
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
                .antMatchers(deploymentWhiteList).permitAll()
                .antMatchers(swaggerPath).permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();

        http.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);


        http
                .logout() // 로그아웃 기능 작동함
                .logoutUrl("/logout") // 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
                .logoutSuccessUrl("/") // 로그아웃 성공 후 이동페이지
                .deleteCookies("JSESSIONID"); // 로그아웃 후 쿠키 삭제

        return http.build();
    }

    @Bean
    @Profile("deploy")
    SecurityFilterChain securityFilterChainDeploy(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(deploymentWhiteList).permitAll()
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);


        http
                .logout() // 로그아웃 기능 작동함
                .logoutUrl("/logout") // 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
                .logoutSuccessUrl("/") // 로그아웃 성공 후 이동페이지
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


