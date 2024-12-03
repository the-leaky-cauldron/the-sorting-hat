package org.theleakycauldron.thesortinghat.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Setter
@Getter
@AllArgsConstructor
public class SortingHatLoginRequestDTO {
    @NotEmpty
    @Email
    private String username;
    @NotEmpty
    private String password;
}
