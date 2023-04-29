package com.naumen.blogeng.config;

import com.naumen.blogeng.model.User;
import com.naumen.blogeng.model.Role;
import com.naumen.blogeng.repository.UserRepository;
import com.naumen.blogeng.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(RoleRepository roleRepository, UserRepository userRepository, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/registration/**").permitAll()
                                .requestMatchers("/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/post/*").permitAll()
                                .requestMatchers(HttpMethod.GET, "/static/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/post/*/comment/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/post/**").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler((request, response, authentication) -> {
                                    String roleName = authentication.getAuthorities().stream()
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalStateException("User has no roles"))
                                            .getAuthority();
                                    Role role = roleRepository.findByName(roleName);
                                    if (role.getName().equals("ROLE_ADMIN")){
                                        response.sendRedirect("/users");
                                    } else {
                                        response.sendRedirect("/");
                                    }
                                })
//                                .defaultSuccessUrl("/")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @PostConstruct
    private void initAdmin() {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        roleRepository.save(adminRole);
        roleRepository.save(userRole);
        User admin = new User();
        admin.setEmail("admin@admin.ru");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        String s = passwordEncoder().encode("admin");
        admin.setPassword(s);
        admin.setRoles(List.of(adminRole, userRole));
        userRepository.save(admin);
    }


}