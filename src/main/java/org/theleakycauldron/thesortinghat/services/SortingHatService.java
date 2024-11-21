package org.theleakycauldron.thesortinghat.services;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public interface SortingHatService {
    String registerUser(String name, String email, String password, String phoneNumber) throws JsonProcessingException;
}
