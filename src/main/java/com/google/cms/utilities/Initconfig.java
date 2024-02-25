package com.google.cms.utilities;

import com.google.cms.models.User;
import com.google.cms.repositories.UserRepository;
import com.google.cms.models.Role;
import com.google.cms.repositories.RoleRepository;
import com.google.cms.utilities.Shared.AuditTrailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.*;

@Component
@Slf4j
public class Initconfig {
    private final ResourceLoader resourceLoader;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuditTrailService auditTrailService;

    public Initconfig(ResourceLoader resourceLoader, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder, AuditTrailService auditTrailService) {
        this.resourceLoader = resourceLoader;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.auditTrailService = auditTrailService;
    }
    public void initSuperRole(){
        log.info("---------------------Init Super-role Check");
        List<Role> roleList = new ArrayList<>();
        List<String> eroles = new ArrayList<>();
        eroles.add(ERole.ROLE_MEMBER.toString());
        eroles.add(ERole.ROLE_ADMIN.toString());
        for (int i=0; i<eroles.size(); i++){
            if (roleRepository.findByName(eroles.get(i)).isEmpty()) {
                Role userRole = new Role();
                userRole.setName(eroles.get(i));
                auditTrailService.POSTAudit(userRole);
                roleList.add(userRole);
            }
        }
        roleRepository.saveAll(roleList);
        log.info("---------------------Complete Super-role Check");
    }
    public void initUsers() throws MessagingException {
        if (!userRepository.existsByUsername("Admin")) {
            Optional<Role> check = roleRepository.findByName(String.valueOf(ERole.ROLE_ADMIN));
            Set<Role> roles = new HashSet<>();
            roles.add(check.get());
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("User");
            user.setEmail("admin@admin.com");
            user.setUsername("Admin");
            user.setRoles(roles);
            user.setPassword(encoder.encode("Admin"));
            auditTrailService.POSTAudit(user);
            userRepository.save(user);
            String mailMessage = "Admin Account# username " + user.getUsername() + " password: Admin";
            log.info("---------------------"+mailMessage);
        }
        if (!userRepository.existsByUsername("Member1")) {
            Optional<Role> check = roleRepository.findByName(String.valueOf(ERole.ROLE_MEMBER));
            Set<Role> roles = new HashSet<>();
            roles.add(check.get());
            User user = new User();
            user.setFirstName("Member1");
            user.setLastName("Member1");
            user.setEmail("member1@member.com");
            user.setModifiedBy("");
            user.setUsername("Member1");
            user.setRoles(roles);
            user.setPassword(encoder.encode("Member1"));
            auditTrailService.POSTAudit(user);
            userRepository.save(user);
            String mailMessage = "Member1 Account# username " + user.getUsername() + " password: Member1";
            log.info("---------------------"+mailMessage);
        }
        if (!userRepository.existsByUsername("Member2")) {
            Optional<Role> check = roleRepository.findByName(String.valueOf(ERole.ROLE_MEMBER));
            Set<Role> roles = new HashSet<>();
            roles.add(check.get());
            User user = new User();
            user.setFirstName("Member2");
            user.setLastName("Member2");
            user.setEmail("member2@member.com");
            user.setModifiedBy("");
            user.setUsername("Member2");
            user.setRoles(roles);
            user.setPassword(encoder.encode("Member2"));
            auditTrailService.POSTAudit(user);
            userRepository.save(user);
            String mailMessage = "Member2 Account# username " + user.getUsername() + " password: Member2";
            log.info("---------------------"+mailMessage);
        }
    }
}
