package com.sample.auth.security.config;

import com.sample.auth.module.Provider;
import com.sample.auth.module.User;
import com.sample.auth.repository.UserRepository;
import com.sample.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OAuth2LoginConfig {

    private final UserRepository userRepository;

    public OAuth2LoginConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Order(1)
    @Bean
    public SecurityFilterChain oauthLoginFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfo) -> userInfo
                                .oidcUserService(this.oidcUserService()) //load user information after successful oauth/oidc login
                        )
                );

        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
          OidcUser oidcUser = delegate.loadUser(userRequest);

          String email = oidcUser.getEmail();
          String name = oidcUser.getFullName();
          String providerId = oidcUser.getSubject();
          String profilePicture = oidcUser.getPicture();

          User user = userRepository.findByEmail(email)
                  .map(existing -> {
                      existing.setName(name);
                      existing.setProfilePicture(profilePicture);
                      return userRepository.save(existing);
                  })
                  .orElseGet(() -> {
                      return userRepository.save(new User(
                              email,
                              name,
                              profilePicture,
                              Provider.GOOGLE,
                              providerId));
                  });

          System.out.println(user);
          return oidcUser;
        };
    }
}
