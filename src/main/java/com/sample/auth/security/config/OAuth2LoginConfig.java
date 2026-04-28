package com.sample.auth.security.config;

import com.sample.auth.handler.OAuth2SuccessHandler;
import com.sample.auth.module.Provider;
import com.sample.auth.module.Role;
import com.sample.auth.module.User;
import com.sample.auth.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OAuth2LoginConfig {

    private final UserRepository userRepository;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    public OAuth2LoginConfig(UserRepository userRepository, OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.userRepository = userRepository;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    @Bean
    public SecurityFilterChain oauthLoginFilterChain(HttpSecurity http) throws Exception{
        http
                .securityMatcher("/oauth2/**")
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfo) -> userInfo
                                .oidcUserService(this.oidcUserService()) // note: load user information after successful oauth/oidc login
                        )
                        .successHandler(oAuth2SuccessHandler)
                );

        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
          OidcUser oidcUser = delegate.loadUser(userRequest);

          String email = oidcUser.getEmail();
          String fullName = oidcUser.getFullName();
          String profilePictureUrl = (String) oidcUser.getClaims()
                  .getOrDefault("picture", null);
          Role role = Role.USER;
          String providerId = oidcUser.getSubject();

          String registrationId = userRequest
                  .getClientRegistration().getRegistrationId();
          Provider provider = Provider.valueOf(registrationId.toUpperCase());

          User user = userRepository.findByEmail(email)
                  .map(existing -> {
                      existing.setFullName(fullName);
                      existing.setProfilePictureUrl(profilePictureUrl);
                      return userRepository.save(existing);
                  })
                  .orElseGet(() -> {
                      return userRepository.save(new User(
                              email,
                              fullName,
                              profilePictureUrl,
                              provider,
                              providerId,
                              role));
                  });

          return oidcUser;
        };
    }
}
