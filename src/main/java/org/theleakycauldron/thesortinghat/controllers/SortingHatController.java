package org.theleakycauldron.thesortinghat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.theleakycauldron.thesortinghat.dtos.SortingHatLoginResponseDTO;
import org.theleakycauldron.thesortinghat.dtos.SortingHatRequestDTO;
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

    @PostMapping
    public ResponseEntity<String> registration(@RequestBody SortingHatRequestDTO requestDTO){
        return ResponseEntity.of(Optional.of(""));
    }

    @PostMapping("/login")
    public ResponseEntity<SortingHatLoginResponseDTO> login(){
        return ResponseEntity.ok(sortingHatService.login());
    }

}
