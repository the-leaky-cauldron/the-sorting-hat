package org.theleakycauldron.thesortinghat.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Getter
@AllArgsConstructor
@Builder
public class SortingHatLoginResponseDTO {
    private String name;
    private String phoneNumber;
    private String email;
    private String token;
}
