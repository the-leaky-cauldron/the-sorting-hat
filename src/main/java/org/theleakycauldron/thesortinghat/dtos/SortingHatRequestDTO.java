package org.theleakycauldron.thesortinghat.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Getter
@Setter
@AllArgsConstructor
public class SortingHatRequestDTO {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @JsonProperty(required = true)
    @Email
    @NotEmpty
    private String email;

    @JsonProperty(required = true)
    @NotEmpty
    private String phoneNumber;

    @JsonProperty(required = true)
    @NotEmpty
    private String password;
}
