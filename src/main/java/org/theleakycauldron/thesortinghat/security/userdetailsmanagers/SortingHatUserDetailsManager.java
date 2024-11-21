package org.theleakycauldron.thesortinghat.security.userdetailsmanagers;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.theleakycauldron.thesortinghat.entities.User;
import org.theleakycauldron.thesortinghat.repositories.SortingHatUserRepository;

import java.util.Optional;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Setter
@Getter
@Component
public class SortingHatUserDetailsManager implements CustomUserDetailsManager {

    private final SortingHatUserRepository sortingHatUserRepository;

    @Autowired
    public SortingHatUserDetailsManager(SortingHatUserRepository sortingHatUserRepository) {
        this.sortingHatUserRepository = sortingHatUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = sortingHatUserRepository.findByEmail(username);
        if (userOptional.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        User user = userOptional.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }


    @Override
    public void updateUser(UserDetails user) {
        String principal = user.getUsername();
        User user1 = sortingHatUserRepository.findByEmail(principal).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user1.setEmail(user.getUsername());
        user1.setPassword(user.getPassword());
        sortingHatUserRepository.save(user1);

    }

    @Override
    public void deleteUser(String username) {
        sortingHatUserRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        sortingHatUserRepository.deleteByEmail(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        User user = sortingHatUserRepository.findByEmail(principal).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(user.getPassword().equals(oldPassword)){
            user.setPassword(newPassword);
            sortingHatUserRepository.save(user);
        }
    }


}
