package com.example.springsecurity_amigoscode.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
@Service
public class JwtService {
    private static final String SECRET_KEY = "CcMo0sdfELXn7w9zcW0IDWSNFsJYDbRSPrMAvT2lhr4iA68mFTQJlNHu/1ZDuvBN";
    /*
    *
    * */

    public String exstractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }
    /*
    *   this generic method to extract the claim
    * */
    public <T> T extractClaim (String token , Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    /*
     * the method for help to generate the token
     *
     * */
    public String generateToken(
            Map<String, Object> extractClaim,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extractClaim) //passing the claim
                .setSubject(userDetails.getUsername()) // pass the subject from username
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24)) //make expriation for 1 day
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) //get the signing key
                .compact(); // the one we generate and get token
    }
    /*
    *  this method user to validate the token
    * if this token is belonge to userdetails
    * */
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = exstractUsername(token);
        return (username.equals(userDetails.getUsername()))&&!isTokenExpired(token); //check the username and token expired
    }
    /*
    * to check token is expired or not
    * */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    /*
    * this method for extracting the expiration of token
    * */
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims (String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()) // when we try to decode the token we need to signing key
                .build() //one the method we are going to call parseclaimjws
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); //the algorithms to get signing key
    }
}
