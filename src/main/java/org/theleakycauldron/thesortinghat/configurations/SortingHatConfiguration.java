package org.theleakycauldron.thesortinghat.configurations;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedReorderedGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Configuration
public class SortingHatConfiguration {
    @Bean
    public TimeBasedReorderedGenerator generator(){
        return Generators.timeBasedReorderedGenerator();
    }

}
