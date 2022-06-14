package pl.sggw.foodsharingservice.config;

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

  private final UserDetailsService userDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }
  // ALL REQUESTS
  //        httpSecurity
  //        .authorizeRequests()
  //        .anyRequest()
  //        .authenticated()
  //        .and()
  //        .formLogin()
  //        .and()
  //        .cors()
  //        .and()
  //        .csrf()
  //        .disable();

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    //                         API REQUESTS
    httpSecurity
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/fss")
        .permitAll()
        .antMatchers("/fss/api/**")
        .authenticated()
        .and()
        .formLogin()
        .defaultSuccessUrl("/swagger-ui/index.html?configUrl=/fss/v3/api-docs/swagger-config");
  }
}
