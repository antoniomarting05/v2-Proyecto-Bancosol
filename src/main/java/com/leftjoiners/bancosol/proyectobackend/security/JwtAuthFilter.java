/*
Javier Urbaneja Benítez: 70%
IA: 30%

Documentación JWT: https://www.geeksforgeeks.org/springboot/spring-boot-3-0-jwt-authentication-with-spring-security-using-mysql-database/
*/

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
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        String username = null;
        String rol = null;
        String nombre = null;

        // Buscamos en la cabecera 'Authorization' (Cuando la petición viene del frontend)
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        // Buscamos en las Cookies (Cuando la petición viene de la JSP)
        if (token == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwtToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }


        if (token != null) {
            try {
                username = jwtUtil.extractUsername(token);
                rol = jwtUtil.extractRol(token);
                nombre = jwtUtil.extractNombre(token);

                System.out.println("Ruta solicitada: " + request.getRequestURI());
                System.out.println("Token recibido para: " + username + " con Rol: " + rol);
                System.out.println("¿Es el token válido para jwtUtil?: " + jwtUtil.validateToken(token));
            } catch (Exception e) {
                System.out.println("Error leyendo token" + e.getMessage());
            }
        }


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

                /*
                Metemos la info básica del usuario en la request porque al ser un servidor
                completamente Stateless, se nos borra el usuario al meterlo en la sesión.

                Esto nos sirve para ver si el rol del usuario tiene los permisos necesarios
                para pinchar en algunos botones. Esta comprobación es simplemente a nivel de
                jsp (el usuario podría dirigirse a esas pestañas escribiendoq la url o
                quitando el disable del botón con F12)

                Para que sea más seguro, en SecurityConfig.java tenemos varios filtros que
                prohiben el acceso a ciertas pestañas a nivel de Backend (compara el rol del
                JWT y no te deja hacer peticion a las distintas url).

                (Sacado de la documentación de Spring Boot).
                */
                Usuario usuarioInfo =  new Usuario();
                usuarioInfo.setUsuario(username);
                usuarioInfo.setNombre(nombre != null ? nombre : "Usuario");
                usuarioInfo.setRol(rol);

                request.setAttribute("user", usuarioInfo);
            }
        }

        filterChain.doFilter(request, response);
    }
}