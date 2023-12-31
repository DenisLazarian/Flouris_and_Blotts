package com.web.app.flourishandblotts.config.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.app.flourishandblotts.config.jwt.JwtUtils;
import com.web.app.flourishandblotts.models.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

//    @Resource
    private final JwtUtils jwtUtils;
    public JwtAuthenticationFilter(JwtUtils JwtUtils){
    this.jwtUtils = JwtUtils;
}


    /**
     * Here we are attempting the user authentication.
     * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username;
        String password;

        try {
            UserEntity userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            username = userEntity.getMail();
            password = userEntity.getPassword();

        }catch (IOException e){
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        return getAuthenticationManager().authenticate(authenticationToken);
    }


    /**
     * If the authentication is successful, here will generate the token.
     *  */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User user  = (User) authResult.getPrincipal();
        String token = this.jwtUtils.generateAccessToken(user.getUsername());

        response.addHeader("Authorization", token);

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);
        httpResponse.put("Message", "Authenticated");
        httpResponse.put("Username", user.getUsername());

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // Personaliza la respuesta en caso de autenticación fallida
        // Puedes crear un objeto de respuesta personalizado
//        ErrorResponse errorResponse = new ErrorResponse("Error de autenticación", "Credenciales incorrectas");
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", failed.getMessage());
        // Convierte el objeto de respuesta en JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        // Establece el código de estado y el tipo de contenido en la respuesta
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        // Escribe la respuesta JSON en el cuerpo de la respuesta
        response.getWriter().write(jsonResponse);
    }
}
