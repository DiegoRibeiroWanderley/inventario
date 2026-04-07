package com.inventario.projeto.security;

import com.inventario.projeto.model.Funcao;
import com.inventario.projeto.model.Usuario;
import com.inventario.projeto.model.enums.Funcoes;
import com.inventario.projeto.repositories.FuncaoRepository;
import com.inventario.projeto.repositories.UsuarioRepository;
import com.inventario.projeto.security.jwt.AuthEntryPointJwt;
import com.inventario.projeto.security.jwt.AuthTokenFilter;
import com.inventario.projeto.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final AuthTokenFilter authJwtTokenFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsServiceImpl);

        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/signin").permitAll()
                                .requestMatchers("/api/auth/user").permitAll()
                                .anyRequest()
                                .authenticated());

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }

    @Bean
    public CommandLineRunner initData(FuncaoRepository roleRepository, UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Funcao userRole = roleRepository.findByFuncaoNome(Funcoes.ROLE_USER)
                    .orElseGet(() -> {
                        Funcao newUserRole = Funcao.builder().funcaoNome(Funcoes.ROLE_USER).build();
                        return roleRepository.save(newUserRole);
                    });

            Funcao adminRole = roleRepository.findByFuncaoNome(Funcoes.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Funcao newAdminRole = Funcao.builder().funcaoNome(Funcoes.ROLE_ADMIN).build();
                        return roleRepository.save(newAdminRole);
                    });

            Set<Funcao> adminRoles = Set.of(adminRole);

            if (!userRepository.existsByNomeDoUsuario("admin")) {
                Usuario admin = Usuario.builder()
                        .nomeDoUsuario("admin")
                        .email("admin@example.com")
                        .senha(passwordEncoder.encode("adminPassword"))
                        .build();
                userRepository.save(admin);
            }

            userRepository.findByNomeDoUsuario("admin").ifPresent(admin -> {
                admin.setFuncoes(adminRoles);
                userRepository.save(admin);
            });
        };
    }
}
