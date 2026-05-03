## 1. Clone repository
```
git clone https://github.com/JustSashaUP/oauth2-jwt
cd oauth2-jwt
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
mvn test -Dtest=JwtKeyGenTest
```
Copy: 
```
JWT_SECRET_KEY=<generated_secret_key>
```
---
## 5. Run with Docker
```
docker compose up --build -d
```
Host:
```
http://localhost:8080
```
