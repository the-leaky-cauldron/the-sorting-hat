package org.theleakycauldron.thesortinghat.configurations;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedReorderedGenerator;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

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

    @Bean
    public NewTopic signupTopic(){
        return TopicBuilder
                .name("user-signup-email")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public StringJsonMessageConverter messageConverter(){
        return new StringJsonMessageConverter();
    }


}
