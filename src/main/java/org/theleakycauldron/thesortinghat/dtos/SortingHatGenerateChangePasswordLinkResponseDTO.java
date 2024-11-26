package org.theleakycauldron.thesortinghat.dtos;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SortingHatGenerateChangePasswordLinkResponseDTO {
    String token;
    @Email
    String email;
}
