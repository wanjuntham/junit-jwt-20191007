package learning.appointmentapp.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import learning.appointmentapp.constants.SecurityConstants;
import learning.appointmentapp.entities.Employee;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * JwtUtils
 */
public class JwtUtils {

    public static String generateJwt(Employee user) {
        List<String> roles = new ArrayList<>();

        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

        String subject = Long.toString(user.getId());

        String token = Jwts.builder().signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE).setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE).setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + 864000000)).claim("rol", roles).compact();

        return token;
    }

    public static UsernamePasswordAuthenticationToken parseJwt(String token) {
        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

        Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);

        String employeeId = parsedToken.getBody().getSubject();

        List<SimpleGrantedAuthority> authorities = Collections.emptyList();

        if (employeeId != null) {
            return new UsernamePasswordAuthenticationToken(employeeId, null, authorities);
        }

        return null;
    }
}