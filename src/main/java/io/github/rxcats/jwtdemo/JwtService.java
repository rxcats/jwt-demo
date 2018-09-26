package io.github.rxcats.jwtdemo;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    private static final String SECRET_CODE = "0123456789";

    private final Map<String, User> users;

    private final AtomicInteger id;

    public JwtService() {
        users = new HashMap<>();
        id = new AtomicInteger();
    }

    public String createToken(String email) {

        User user = users.get(email);

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setUserId(String.valueOf(id.addAndGet(1)));
        }

        LocalDateTime expire = LocalDateTime.now().plusHours(1);
        Date exp = Date.from(expire.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
            .setExpiration(exp)
            .setId(user.getUserId())
            .claim("userId", user.getUserId())
            .claim("email", email)
            .claim("now", System.currentTimeMillis())
            .signWith(SignatureAlgorithm.HS256, SECRET_CODE.getBytes(StandardCharsets.UTF_8))
            .compact();

    }

    public String getUserId(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
            .setSigningKey(SECRET_CODE.getBytes(StandardCharsets.UTF_8))
            .parseClaimsJws(token);

        return claimsJws.getBody().getId();
    }

    public boolean verifyToken(String token, String userId) {

        Jws<Claims> claimsJws = Jwts.parser()
            .setSigningKey(SECRET_CODE.getBytes(StandardCharsets.UTF_8))
            .parseClaimsJws(token);

        return claimsJws.getBody().getId().equals(userId);

    }

}
