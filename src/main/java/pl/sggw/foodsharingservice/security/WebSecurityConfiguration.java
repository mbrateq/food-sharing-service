package pl.sggw.foodsharingservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final PasswordEncoderService passwordEncoderService;

  private final UserDetailsService userDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeRequests()
        //                ALL REQUESTS
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .and()
//        .cors()
//        .and()
        .csrf()
        .disable();
    //                     API REQUESTS
    //                    .antMatchers("/api/**").authenticated().and().formLogin();
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    //    return NoOpPasswordEncoder.getInstance();
    return passwordEncoderService.getPasswordEncoder();
  }
}
