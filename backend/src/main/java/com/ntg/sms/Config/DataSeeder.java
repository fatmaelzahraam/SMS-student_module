package com.ntg.sms.Config;

import com.ntg.sms.Entities.Role;
import com.ntg.sms.Entities.Student;
import com.ntg.sms.Entities.User;
import com.ntg.sms.Repositories.RoleRepository;
import com.ntg.sms.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role studentRole = roleRepository.findByRoleName("STUDENT").orElseGet(
                () -> {
                    Role role = Role.builder().roleName("STUDENT").build();
                    return roleRepository.save(role);
                }
        );

        if (userRepository.findByFirstName("Fatma Elzahraa").isEmpty()){
            User admin = User.builder()
                    .firstName("Fatma Elzahraa")
                    .lastName("Mohammed")
                    .email("fatoma688@gmail.com")
                    .address("20 , Cairo , Egypt")
                    .firstNameInArabic("فاطمة الزهراء")
                    .lastNameInArabic("محمد")
                    .password(passwordEncoder.encode("123456"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('M')
                    .nationality("Egyption")
                    .birthDate(LocalDate.now())
                    .religion("muslim")
                    .nationalNumber(30911150101074L)
                    .role(studentRole)
                    .build();
            userRepository.save(admin);

            Student student = Student.builder().user(admin).governorate("cairo").academicScoreInMiddleSchool(266L).placeOfBirth("Cairo").build();
        }
    }
}
