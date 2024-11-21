package org.theleakycauldron.thesortinghat.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Entity(name = "users")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class User extends BaseModel {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
