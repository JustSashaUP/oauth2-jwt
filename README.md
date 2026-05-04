# Spring Security OAuth2 + JWT (Minimal & Production-Ready)

A clean and minimal implementation of OAuth2 login (Google) with JWT-based authentication using Spring Security.

## 🚀 Features
- OAuth2 login (Google)
- Stateless JWT authentication
- No session storage

## 1. Clone repository
```
git clone https://github.com/JustSashaUP/spring-oauth2-jwt-example.git
cd spring-oauth2-jwt-example
```
## 2. Create the environment file with pre-defined variables
```
cat <<EOF > .env
POSTGRES_DB=
POSTGRES_USER=
POSTGRES_PASSWORD=

SPRING_DATASOURCE_URL=jdbc:postgresql://<service-name>:<port>/<database-name>
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=

GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=
GOOGLE_CLIENT_SCOPE=openid,email,profile

JWT_SECRET_KEY=
EOF
```
## 3. Configure Google OAuth 2.0
1. Go to Google Cloud Console
2. Create OAuth 2.0 Client ID
3. Add redirect URI:
```
http://localhost:8080/login/oauth2/code/google
```
Copy:
```
GOOGLE_CLIENT_ID=<client_id>
GOOGLE_CLIENT_SECRET=<secret_key>
```
## 4. Generate the JWT secret key
```
mvn test -Dtest=JwtSecretKeyGenTest
```
Copy: 
```
JWT_SECRET_KEY=<generated_secret_key>
```
## 5. Run with Docker
```
docker compose up --build -d
```
Host:
```
http://localhost:8080
```
---
## ❗ Common Issues

- Filter called twice → do NOT register as @Component
- JWT not working → check secret consistency
- OAuth2 redirect fails → verify redirect URI
---

## 🔗 Useful Links from me

### 🔐 Spring Security — OAuth2 / OIDC (Login Only)

- 💡 How FilterChain works (my notes)  
  https://www.linkedin.com/posts/oleksandr-savchenko-08213924a_springsecurity-springboot-java-share-7452790826654425088-fZtA?utm_source=social_share_send&utm_medium=member_desktop_web&rcm=ACoAAD2ZnCIBqBUWYVhf-bkzFSKE_a_141K45Ug
- 📘 Official Spring Security OAuth2 Login Docs  
  https://docs.spring.io/spring-security/reference/servlet/oauth2/login/index.html  
- 📘 OpenID Connect (OIDC) Support  
  https://docs.spring.io/spring-security/reference/servlet/oauth2/login/advanced.html#oauth2login-oidc  
- 📘 OAuth2 Client Overview  
  https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html#oauth2-client
- 📘 Spring Boot OAuth2 Login Guide  
  https://spring.io/guides/tutorials/spring-boot-oauth2/  

### 🔑 JWT (JSON Web Token)

- 📘 JWT Introduction  
  https://www.jwt.io/introduction#what-is-json-web-token-structure
- 📘 RFC 7519 — JWT Standard  
  https://datatracker.ietf.org/doc/html/rfc7519

### 🔄 OAuth2 vs OIDC (Conceptual)

- 📘 OAuth 2.0 Framework (RFC 6749)  
  https://datatracker.ietf.org/doc/html/rfc6749  
- 📘 OpenID Connect Core Spec  
  https://openid.net/specs/openid-connect-core-1_0.html  
