package pl.sggw.foodsharingservice.security;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Configuration
@RequiredArgsConstructor
public class PasswordEncoderConfiguration {

    private final PasswordEncoderService passwordEncoderService;


    @Bean(name = "passwordEncoder")
    public PasswordEncoder getPasswordEncoder(){
        return passwordEncoderService.getPasswordEncoder();
    }
}
