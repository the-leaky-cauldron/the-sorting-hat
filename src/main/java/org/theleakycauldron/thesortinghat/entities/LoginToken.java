package org.theleakycauldron.thesortinghat.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="tokens")
public class LoginToken extends BaseModel{
    @Column(length = 500)
    private String token;
    private LocalDateTime expiresAt;
}
