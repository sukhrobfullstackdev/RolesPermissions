package uz.sudev.rolespermissions.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.sudev.rolespermissions.entity.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JWTProvider {
    Date expireDate = new Date(System.currentTimeMillis() + expireTime);
    private static final long expireTime = 1000 * 60 * 60 * 24;
    private static final String secretKey = "Shirin";

    public String generateToken(String username, Role role) {
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("role", role.getName())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


    public String getEmailFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
