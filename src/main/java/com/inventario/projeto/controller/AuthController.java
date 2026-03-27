package com.inventario.projeto.controller;

import com.inventario.projeto.exception.NotFoundException;
import com.inventario.projeto.model.Funcao;
import com.inventario.projeto.model.Usuario;
import com.inventario.projeto.model.enums.Funcoes;
import com.inventario.projeto.repositories.FuncaoRepository;
import com.inventario.projeto.repositories.UsuarioRepository;
import com.inventario.projeto.security.jwt.JwtUtils;
import com.inventario.projeto.security.request.LoginRequest;
import com.inventario.projeto.security.request.SignupRequest;
import com.inventario.projeto.security.response.MessageResponse;
import com.inventario.projeto.security.response.UserInfoResponse;
import com.inventario.projeto.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    private final UsuarioRepository userRepository;
    private final FuncaoRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByNomeDoUsuario(signupRequest.getNomeDoUsuario())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken."));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken."));
        }

        Usuario usuario = Usuario.builder()
                .nomeDoUsuario(signupRequest.getNomeDoUsuario())
                .email(signupRequest.getEmail())
                .senha(passwordEncoder.encode(signupRequest.getSenha()))
                .build();

        Set<String> strFuncoes = signupRequest.getFuncao();
        Set<Funcao> funcoes = new HashSet<>();

        if (strFuncoes == null) {
            Funcao funcaoDoUsuario = roleRepository.findByFuncaoNome(Funcoes.ROLE_USER)
                    .orElseThrow(() -> new NotFoundException("Role", Funcoes.ROLE_USER.toString()));
            funcoes.add(funcaoDoUsuario);
        } else {
            strFuncoes.forEach(funcao -> {
                if (funcao.equals("admin")) {
                    Funcao funcaoDoUsuarioAdmin = roleRepository.findByFuncaoNome(Funcoes.ROLE_ADMIN)
                            .orElseThrow(() -> new NotFoundException("Role", Funcoes.ROLE_ADMIN.toString()));
                    funcoes.add(funcaoDoUsuarioAdmin);
                } else {
                    Funcao funcaoDoUsuario = roleRepository.findByFuncaoNome(Funcoes.ROLE_USER)
                            .orElseThrow(() -> new NotFoundException("Role", Funcoes.ROLE_USER.toString()));
                    funcoes.add(funcaoDoUsuario);
                }
            });
        }
        usuario.setFuncoes(funcoes);
        userRepository.save(usuario);
        return ResponseEntity.ok().body(new MessageResponse("Usuário cadastrado com sucesso!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authentication(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getNomeDoUsuario(), loginRequest.getSenha())
            );
        } catch (AuthenticationException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Credenciais inválidas");
            map.put("status", false);

            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        assert userDetails != null;
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookies(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        UserInfoResponse loginResponse = new UserInfoResponse(
                userDetails.getId(),
                userDetails.getNomeDoUsuario(),
                roles);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(loginResponse);
    }

    @GetMapping("/username")
    public String currentUsername(Authentication authentication) {
        if (authentication != null) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signoutUser() {
        ResponseCookie cookie = jwtUtils.generateCleanJwtCookies();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("Deslogado com sucesso!"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> currentUser(Authentication authentication) {
        if (authentication != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            assert userDetails != null;
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            UserInfoResponse loginResponse = new UserInfoResponse(
                    userDetails.getId(),
                    userDetails.getUsername(),
                    roles);

            return ResponseEntity.ok().body(loginResponse);
        } else {
            return null;
        }
    }
}
