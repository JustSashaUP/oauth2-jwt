package com.sample.auth.handler;

import com.sample.auth.module.User;
import com.sample.auth.service.JwtService;
import com.sample.auth.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserService userService;

    private final String redirectUrl;

    public OAuth2SuccessHandler(JwtService jwtService, UserService userService,
                                @Value("${cookie.url}") String redirectUrl) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        User user = userService.findOrCreateUser(oidcUser.getEmail());

        String token = jwtService.generateToken(user);

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // milliseconds
        cookie.setSecure(false); // HTTPS only

        response.addCookie(cookie);
        response.sendRedirect("/");
    }
}
