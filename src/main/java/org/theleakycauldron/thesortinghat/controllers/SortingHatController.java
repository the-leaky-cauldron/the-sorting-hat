package org.theleakycauldron.thesortinghat.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.theleakycauldron.thesortinghat.commons.SortingHatUserUtils;
import org.theleakycauldron.thesortinghat.dtos.*;
import org.theleakycauldron.thesortinghat.services.SortingHatService;

import java.util.Optional;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@RestController
public class SortingHatController {

    private final SortingHatService sortingHatService;


    @Autowired
    public SortingHatController(SortingHatService sortingHatService){
        this.sortingHatService  = sortingHatService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SortingHatResponseDTO> registration(
            @RequestBody @Valid SortingHatRequestDTO requestDTO) throws JsonProcessingException {
        String name = requestDTO.getFirstName() + " " + requestDTO.getLastName();
        String email = requestDTO.getEmail();
        String password = requestDTO.getPassword();
        String phoneNumber = requestDTO.getPhoneNumber();

        SortingHatResponseDTO responseDTO = SortingHatUserUtils
                .convertToSortingHatResponseDTO(
                        sortingHatService.registerUser(name, email, password, phoneNumber)
                );

        return ResponseEntity.of(Optional.of(responseDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<SortingHatLoginResponseDTO> login(){
        return ResponseEntity.ok(sortingHatService.login());
    }

    @GetMapping("/password")
    public ResponseEntity<SortingHatGenerateChangePasswordLinkResponseDTO> generatePasswordLink(
            @RequestBody @Valid SortingHatUserDetailsRequestDTO requestDTO) throws JsonProcessingException{
        String email = requestDTO.getEmail();
        return ResponseEntity.ok(sortingHatService.generateChangePasswordLink(email));
    }

    @PostMapping("/password")
    public ResponseEntity<SortingHatResponseDTO> changePassword(
            @RequestBody @Valid SortingHatChangePasswordRequestDTO requestDTO) {
        String email = requestDTO.getEmail();
        String newPassword = requestDTO.getNewPassword();

        return ResponseEntity.ok(SortingHatUserUtils
                .convertToGeneratePasswordLinkDTO(
                        sortingHatService.changePassword(email, newPassword) ?
                                "Change the password"
                                : "There is some error in the request"
                ));
    }

}
