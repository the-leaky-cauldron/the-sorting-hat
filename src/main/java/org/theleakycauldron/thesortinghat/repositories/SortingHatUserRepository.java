package org.theleakycauldron.thesortinghat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.theleakycauldron.thesortinghat.entities.User;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public interface SortingHatUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
}
