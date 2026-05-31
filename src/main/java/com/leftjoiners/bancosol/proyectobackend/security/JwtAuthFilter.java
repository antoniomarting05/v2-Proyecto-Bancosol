package com.leftjoiners.bancosol.proyectobackend.security;

import com.leftjoiners.bancosol.proyectobackend.dto.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil; // Debe ser final para que Lombok lo incluya

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        String username = null;
        String rol = null;
        String nombre = null;

        // 1. Buscamos en la cabecera 'Authorization' (Para REST / Angular / React)
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        // 2. Si no está, buscamos en las Cookies (Para tus JSPs)
        if (token == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwtToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        // 3. Validamos
        if (token != null) {
            try {
                username = jwtUtil.extractUsername(token);
                rol = jwtUtil.extractRol(token);
                nombre = jwtUtil.extractNombre(token);
            } catch (Exception e) {
                System.out.println("Error leyendo token");
            }
        }

        // 4. Autenticamos
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(token)) {
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();

                if (rol != null) {
                    authorities.add(new SimpleGrantedAuthority(rol));
                }

                UserDetails userDetails = new User(username, "", authorities);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);

                Usuario usuarioInfo =  new Usuario();
                usuarioInfo.setUsuario(username);
                usuarioInfo.setNombre(nombre != null ? nombre : "Usuario");

                request.setAttribute("user", usuarioInfo);
            }
        }

        filterChain.doFilter(request, response);
    }
}