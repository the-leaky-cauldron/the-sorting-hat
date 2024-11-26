package org.theleakycauldron.thesortinghat.security.userdetailsmanagers;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

public interface CustomUserDetailsManager {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    void changePassword(String oldPassword, String newPassword);
    void updateUser(UserDetails user);
    void deleteUser(String username);
}
