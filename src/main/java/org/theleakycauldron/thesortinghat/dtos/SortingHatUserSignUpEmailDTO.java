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
@Setter
@AllArgsConstructor
@Builder
public class SortingHatUserSignUpEmailDTO {
    private String name;
    private String email;
}
