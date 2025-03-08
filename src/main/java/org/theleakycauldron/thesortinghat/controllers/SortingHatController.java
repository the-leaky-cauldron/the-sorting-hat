package org.theleakycauldron.thesortinghat.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.theleakycauldron.thesortinghat.commons.SortingHatUserUtils;
import org.theleakycauldron.thesortinghat.dtos.SortingHatLoginResponseDTO;
import org.theleakycauldron.thesortinghat.dtos.SortingHatRequestDTO;
import org.theleakycauldron.thesortinghat.dtos.SortingHatResponseDTO;
import org.theleakycauldron.thesortinghat.services.SortingHatService;

import java.util.Optional;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@RestController
@RequestMapping("/thesortinghat")
@Tag(name = "Sorting Hat API", description = "API for authentication and authorization of Diagon Alley")
public class SortingHatController {

    private final SortingHatService sortingHatService;


    public SortingHatController(SortingHatService sortingHatService){
        this.sortingHatService  = sortingHatService;
    }

    @PostMapping("/signup")
    @Operation(summary = "Signup user", description = "Returns a thanking message as response")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "500", description = "any server error")
    })
    public ResponseEntity<SortingHatResponseDTO> registration(@RequestBody @Valid SortingHatRequestDTO requestDTO) throws JsonProcessingException {
        String name = requestDTO.getFirstName() + " " + requestDTO.getLastName();
        String email = requestDTO.getEmail();
        String password = requestDTO.getPassword();
        String phoneNumber = requestDTO.getPhoneNumber();
        SortingHatResponseDTO responseDTO = SortingHatUserUtils.convertToSortingHatResponseDTO(sortingHatService.registerUser(name, email, password, phoneNumber));
        return ResponseEntity.of(Optional.of(responseDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Returns a token as response")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User logged in successfully"),
        @ApiResponse(responseCode = "500", description = "any server error")
    })
    public ResponseEntity<SortingHatLoginResponseDTO> login(){
        return ResponseEntity.ok(sortingHatService.login());
    }

}

