package org.theleakycauldron.thesortinghat.commons;

import org.theleakycauldron.thesortinghat.dtos.SortingHatResponseDTO;
import org.theleakycauldron.thesortinghat.dtos.SortingHatLoginResponseDTO;

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
    public static SortingHatResponseDTO convertToGeneratePasswordLinkDTO(String message){
        return SortingHatResponseDTO.builder()
                .message(message)
                .build();
    }
    public static SortingHatLoginResponseDTO convertToLoginResponseDTO(String token, String username, String email) {
        return SortingHatLoginResponseDTO.builder()
                .name(username)
                .email(username)
                .build();

    }
}
