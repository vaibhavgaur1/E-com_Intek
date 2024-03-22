package com.e_commerce._configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private static final String[] WHITE_LIST_URL = {
            "/loginAuth/**", "/loginAuth/registerNewUser"
            , "/product/getAllProducts",
//            "/product/deleteProductDetails/{productId}",
            "/product/getProductById/{productId}","/loginAuth/otpVerify",
            "/loginAuth/generate-otp",
            // updated due to jwt error


    },
    ADMIN_URL={
            "/admin"
    },
    USER_URL={
            "/user"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request-> request
                        .requestMatchers(WHITE_LIST_URL ).permitAll()
                        .requestMatchers(HttpHeaders.ALLOW).permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .exceptionHandling(handler->handler.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        ;
                return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider= new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
}
