package org.theleakycauldron.thesortinghat.dtos;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

/*
 * This DTO is used when the Request has the User Details
 * That include the email id because it is the primary_key to the database
 */

@Getter
@Setter
public class SortingHatUserDetailsRequestDTO {
    /*
     * This is the email of the user sent in the request.
     */
    @Email
    private String email;
}
