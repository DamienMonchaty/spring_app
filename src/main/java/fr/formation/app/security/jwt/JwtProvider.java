package fr.formation.app.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import fr.formation.app.security.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

//JwtProvider est responsable de :
//- la génération
//- la validation 
//- l'extraction des informations d'un jeton JWT

//Un JWT est constitué de trois parties séparées par des points (.) :
//- Header (en-tête) : Contient les métadonnées sur le type de jeton 
//et l'algorithme de signature utilisé.
//- Payload (charge utile) : Contient les revendications (claims) 
//qui sont les informations
//- Signature : Utilisée pour vérifier que le JWT est valide 
//et qu'il n'a pas été modifié

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    
    @Value("${fr.formation.app.jwtSecret}")
    private String jwtSecret;
    
    @Value("${fr.formation.app.jwtExpiration}")
    private int jwtExpiration;
    
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
        		.setSubject((user.getUsername()))
        		.setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
        		.setSigningKey(jwtSecret)
        		.parseClaimsJws(token)
        		.getBody()
        		.getSubject();
    }
    
    public boolean validateJwtToken(String authToken) throws SignatureException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }
        return false;
    }
}
