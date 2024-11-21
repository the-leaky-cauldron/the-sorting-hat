package org.theleakycauldron.thesortinghat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.theleakycauldron.thesortinghat.entities.Role;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public interface SortingHatRoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
}
