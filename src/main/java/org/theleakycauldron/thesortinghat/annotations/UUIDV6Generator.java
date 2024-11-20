package org.theleakycauldron.thesortinghat.annotations;

import org.hibernate.annotations.IdGeneratorType;

import org.theleakycauldron.thesortinghat.generators.SortedUUIDGenerator;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@IdGeneratorType(SortedUUIDGenerator.class)
public @interface UUIDV6Generator {
}
