package com.web.app.flourishandblotts.config;

import com.web.app.flourishandblotts.config.filters.JwtAuthenticationFilter;
import com.web.app.flourishandblotts.config.filters.JwtAuthorizationFilter;
import com.web.app.flourishandblotts.config.jwt.JwtUtils;
import com.web.app.flourishandblotts.services.UserDetailsServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebMvc
public class SecurityConfig {

    @Resource
    JwtUtils jwtUtils;

    @Resource
    UserDetailsServiceImpl userDetailsService;

    @Resource
    JwtAuthorizationFilter authorizationFilter;

    @Resource
    CorsConfig configCors;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(this.jwtUtils);

        // Change the authentication manager settings
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");



        return http
                .cors().and()
                .csrf(AbstractHttpConfigurer::disable)  // config -> config.disable()
                .authorizeHttpRequests(auth->{
                    // manage routing by permission
                    auth.requestMatchers( // here we allow without authentication or any filer, to user this uris
                            "/hello",
//                            "/login",
                            "user/role",
                            "file/**",
                            "book/**"
                    ).permitAll();
                    auth.anyRequest().authenticated(); // here we specify the application to not allow any request to use, except the uris specified previously
                })
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(this.authorizationFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore((Filter) this.configCors, (Class<? extends Filter>) CorsConfig.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @SuppressWarnings("removal")
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http,
                                                PasswordEncoder passwordEncoder) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }
}
