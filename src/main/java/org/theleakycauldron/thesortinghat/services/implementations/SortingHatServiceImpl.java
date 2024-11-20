package org.theleakycauldron.thesortinghat.services.implementations;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.theleakycauldron.thesortinghat.dtos.SortingHatLoginResponseDTO;
import org.theleakycauldron.thesortinghat.entities.LoginToken;
import org.theleakycauldron.thesortinghat.repositories.SortingHatTokenRepository;
import org.theleakycauldron.thesortinghat.services.SortingHatService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Timestamp.valueOf;


/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Service
public class SortingHatServiceImpl implements SortingHatService {

    private final SortingHatTokenRepository sortingHatTokenRepository;
    @Value("${jwt.secret}")
    private String secret;
    private SecretKey key;


    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public SortingHatServiceImpl(
            SortingHatTokenRepository sortingHatTokenRepository
    ){
        this.sortingHatTokenRepository = sortingHatTokenRepository;
    }

    @Override
    public SortingHatLoginResponseDTO login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        LoginToken token = createToken(username, authorities);
        sortingHatTokenRepository.save(token);
        // TODO: Post merging add name and phone number to response
        return SortingHatLoginResponseDTO.builder()
                .email(username)
                .token(token.getToken())
                .build();
    }

    private LoginToken createToken(String username, List<String> authorities){
        Map<String, String> claims = new HashMap<>();
        claims.put("email", username);
        claims.put("role", String.join(",", authorities));
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plusDays(10);
        String token = Jwts.builder()
                .claims(claims)
                .expiration(valueOf(now))
                .issuedAt(valueOf(now))
                .signWith(key)
                .compact();
        return LoginToken.builder()
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(false)
                .expiresAt(expiryTime)
                .token(token)
                .build();
    }
}
