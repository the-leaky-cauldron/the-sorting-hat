package org.theleakycauldron.thesortinghat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@EnableWebSecurity
@Configuration
public class SortingHatSecurityConfig {

    @Bean
    public SecurityFilterChain sortingHatFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(
                        authorize -> {
                            authorize
                                    .requestMatchers("/signup")
                                    .permitAll()
                                    .anyRequest()
                                    .authenticated();
                        }

                )

                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public UserDetailsService getUserDetailsService(){

        return new JdbcUserDetailsManager();
    }
}
