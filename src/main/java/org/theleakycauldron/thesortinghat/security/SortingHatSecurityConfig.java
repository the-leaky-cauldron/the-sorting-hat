package org.theleakycauldron.thesortinghat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.theleakycauldron.thesortinghat.security.userdetailsmanagers.CustomUserDetailsManager;

import javax.sql.DataSource;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@EnableWebSecurity
@Configuration
public class SortingHatSecurityConfig {
    private final CustomUserDetailsManager userDetailsManager;

    public SortingHatSecurityConfig(CustomUserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @Bean
    public SecurityFilterChain sortingHatFilterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        authorize -> {
                            authorize
                                    .requestMatchers("/signup")
                                    .permitAll()
                                    .anyRequest()
                                    .authenticated();
                        }

                )
                .authenticationProvider(getCustomAuthenticationProvider())
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider getCustomAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsManager::loadUserByUsername);
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
