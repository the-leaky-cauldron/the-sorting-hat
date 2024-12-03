package org.theleakycauldron.thesortinghat.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.theleakycauldron.thesortinghat.dtos.SortingHatGenerateChangePasswordLinkResponseDTO;
import org.theleakycauldron.thesortinghat.dtos.SortingHatUserSignUpEmailDTO;
import org.theleakycauldron.thesortinghat.entities.User;
import org.theleakycauldron.thesortinghat.repositories.SortingHatRoleRepository;
import org.theleakycauldron.thesortinghat.repositories.SortingHatUserRepository;
import org.theleakycauldron.thesortinghat.services.SortingHatService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.theleakycauldron.thesortinghat.dtos.SortingHatLoginResponseDTO;
import org.theleakycauldron.thesortinghat.entities.LoginToken;
import org.theleakycauldron.thesortinghat.repositories.SortingHatTokenRepository;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Timestamp.valueOf;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Service
public class SortingHatServiceImpl implements SortingHatService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SortingHatTokenRepository sortingHatTokenRepository;
    private final SortingHatUserRepository sortingHatUserRepository;

    @Value("${jwt.secret}")
    private String secret;
    private SecretKey key;

    @Autowired
    public SortingHatServiceImpl(
            SortingHatUserRepository userRepository,
            SortingHatRoleRepository roleRepository,
            KafkaTemplate<String, String> kafkaTemplate,
            SortingHatTokenRepository sortingHatTokenRepository,
            SortingHatUserRepository sortingHatUserRepository
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.sortingHatTokenRepository = sortingHatTokenRepository;
        this.sortingHatUserRepository = sortingHatUserRepository;
    }

    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String registerUser(String name, String email, String password, String phoneNumber) throws JsonProcessingException {
        Optional<User> userOptional = sortingHatUserRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            // TODO: Convert this to custom exception. Incorporate Controller Advice
            throw new RuntimeException("User already exists");
        }

        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(false)
                .build();
        User savedUser = sortingHatUserRepository.save(user);
        SortingHatUserSignUpEmailDTO sortingHatUserSignUpEmailDTO = SortingHatUserSignUpEmailDTO
                .builder()
                .name(name)
                .email(email)
                .build();

        kafkaTemplate.send("user-signup-email", sortingHatUserSignUpEmailDTO.toString());

        return savedUser.getName();
    }

    @Override
    public SortingHatLoginResponseDTO login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        System.out.println(username);

        LoginToken token = createLoginToken(username, ChronoUnit.DAYS,10, "login");
        User user = sortingHatUserRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        sortingHatTokenRepository.save(token);

        return SortingHatLoginResponseDTO.builder()
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(username)
                .token(token.getToken())
                .build();
    }

    @Override
    public boolean changePassword(String email, String newPassword){
        Optional<User> userOptional = sortingHatUserRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            // TODO: Convert this to custom exception. Incorporate Controller Advice
            // TODO: Figure how to send the custom exception message to client
            throw new RuntimeException("User doesn't exist");
        }

        User user = userOptional.get();

        user.setPassword(newPassword);

        User value = sortingHatUserRepository.save(user);

        return true;
    }


    @Override
    public SortingHatGenerateChangePasswordLinkResponseDTO generateChangePasswordLink(String email) {
        Optional<User> userOptional = sortingHatUserRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            // TODO: Convert this to custom exception. Incorporate Controller Advice
            // TODO: Figure how to send the custom exception message to client
            throw new RuntimeException("User doesn't exist");
        }

        Map<String, String> claims = new HashMap<>();

        claims.put("email", email);
        claims.put("code", "password-change-link");

        String token = createToken(claims, ChronoUnit.MINUTES, 4);

        kafkaTemplate.send("generated-change-password-link", token);

        return SortingHatGenerateChangePasswordLinkResponseDTO.builder()
                .token(token)
                .email(email)
                .build();
    }

    private LoginToken createLoginToken(String userName, ChronoUnit units, int expiryValue, String code){
        Map<String, String> claims = new HashMap<>();

        claims.put("email", userName);
        claims.put("code", code);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plus(expiryValue, units);

        String token = createToken(claims, units, expiryValue);

        return LoginToken.builder()
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(false)
                .expiresAt(expiryTime)
                .token(token)
                .build();
    }

    private String createToken(Map<String, ?> claims, ChronoUnit units, int expiryValue){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plus(expiryValue, units);

        return Jwts.builder()
                .claims(claims)
                .expiration(valueOf(expiryTime))
                .issuedAt(valueOf(now))
                .signWith(key)
                .compact();
    }
}
