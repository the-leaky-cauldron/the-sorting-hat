package org.theleakycauldron.thesortinghat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.theleakycauldron.thesortinghat.entities.LoginToken;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Repository
public interface SortingHatTokenRepository extends JpaRepository<LoginToken, UUID> {
    Optional<LoginToken> findByToken(String token);
}
