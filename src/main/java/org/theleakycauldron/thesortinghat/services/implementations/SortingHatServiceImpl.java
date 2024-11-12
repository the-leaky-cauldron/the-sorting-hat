package org.theleakycauldron.thesortinghat.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.theleakycauldron.thesortinghat.dtos.SortingHatUserSignUpEmailDTO;
import org.theleakycauldron.thesortinghat.entities.Role;
import org.theleakycauldron.thesortinghat.entities.User;
import org.theleakycauldron.thesortinghat.repositories.SortingHatRoleRepository;
import org.theleakycauldron.thesortinghat.repositories.SortingHatUserRepository;
import org.theleakycauldron.thesortinghat.services.SortingHatService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Service
public class SortingHatServiceImpl implements SortingHatService {

    private final SortingHatUserRepository userRepository;
    private final SortingHatRoleRepository roleRepository;
    private final KafkaTemplate<String, SortingHatUserSignUpEmailDTO> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public SortingHatServiceImpl(
            SortingHatUserRepository userRepository,
            SortingHatRoleRepository roleRepository,
//            KafkaTemplate<String, String> kafkaTemplate,
            KafkaTemplate<String, SortingHatUserSignUpEmailDTO> kafkaTemplate,
            ObjectMapper objectMapper
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }
    @Override
    public String registerUser(String name, String email, String password, String phoneNumber) throws JsonProcessingException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        Optional<Role> roleOptional = roleRepository.findByName("USER");
        if(userOptional.isPresent()){
            // TODO: Convert this to custom exception. Incorporate Controller Advice
            throw new RuntimeException("User already exists");
        }
        if(roleOptional.isEmpty()){
            // TODO: Convert this to custom exception. Incorporate Controller Advice
            throw new RuntimeException("Role not found");
        }
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleOptional.get());
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .role(roleList)
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(false)
                .build();
        User savedUser = userRepository.save(user);
        SortingHatUserSignUpEmailDTO sortingHatUserSignUpEmailDTO = SortingHatUserSignUpEmailDTO
                .builder()
                .name(name)
                .email(email)
                .build();
//        String dto = objectMapper.writeValueAsString(sortingHatUserSignUpEmailDTO);
//        kafkaTemplate.send("user-signup-email", dto);
            kafkaTemplate.send("user-signup-email", sortingHatUserSignUpEmailDTO);
        return savedUser.getName();
    }
}
