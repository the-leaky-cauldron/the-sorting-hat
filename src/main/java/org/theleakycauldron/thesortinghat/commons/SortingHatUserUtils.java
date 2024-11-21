package org.theleakycauldron.thesortinghat.commons;

import org.theleakycauldron.thesortinghat.dtos.SortingHatResponseDTO;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public class SortingHatUserUtils {

    public static SortingHatResponseDTO convertToSortingHatResponseDTO(String name){
        return SortingHatResponseDTO.builder()
                .message("Hey " + name + "!, Thanks for signing up!")
                .build();
    }

}
