package org.theleakycauldron.thesortinghat.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;

@Getter
@Setter
@AllArgsConstructor
public class SortingHatChangePasswordRequestDTO {
    String email;
    String newPassword;
}
