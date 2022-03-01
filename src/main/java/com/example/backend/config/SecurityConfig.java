package com.example.backend.config;

import com.example.backend.config.token.TokenFilterConfigurer;
import com.example.backend.process.service.token.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final String[] PUBLIC = {
            "/user/register",
            "/user/login",
            "/admin/login",
            "/uploads/**",
//            "/upload/**",
            "/news/**",
            "/swagger-ui.html/**",
            "/api/**",
            "/test/**",
            "/loginSocial/getToken"
    };
    private final TokenService tokenService;

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(config -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowCredentials(true);
//                    Configuration.setAllowedOriginPatterns(Collections.singletonList("http://*"));
                    corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("http://*"));
//                    corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("https://*"));
                    corsConfiguration.addAllowedHeader("*");
                    corsConfiguration.addAllowedMethod("GET");
                    corsConfiguration.addAllowedMethod("POST");
                    corsConfiguration.addAllowedMethod("PUT");
                    corsConfiguration.addAllowedMethod("DELETE");
//                    Configuration.addAllowedMethod("OPTIONS");

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", corsConfiguration);

                    config.configurationSource(source);
                }).csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(PUBLIC).anonymous()
                .anyRequest().authenticated()
                .and().apply(new TokenFilterConfigurer(tokenService));
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().disable().csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().authorizeRequests()
//                .antMatchers(PUBLIC)
//                .anonymous()
//                .anyRequest()
//                .authenticated()
//                .and().apply(new TokenFilterConfigurer(tokenService));
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
////        config.addAllowedOrigin("http://localhost/4200");
//        config.setAllowedOriginPatterns(Arrays.asList("http://localhost*"));
//        config.addAllowedHeader("*");
////        config.addAllowedMethod("OPTIONS");
////        config.addAllowedMethod("POST");
////        config.addAllowedMethod("GET");
////        config.addAllowedMethod("PUT");
////        config.addAllowedMethod("DELETE");
//        config.addAllowedMethod("*");
//
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

}
