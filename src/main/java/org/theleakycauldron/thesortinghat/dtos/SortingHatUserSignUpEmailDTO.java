package org.theleakycauldron.thesortinghat.dtos;

import lombok.*;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class SortingHatUserSignUpEmailDTO {
    private String name;
    private String email;
}
