package pl.sggw.foodsharingservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sggw.foodsharingservice.security.PasswordEncoderService;

@Slf4j
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class TestController {

    private final PasswordEncoderService passwordEncoderService;

  @GetMapping(value = "/hello", produces = "text/plain")
  public ResponseEntity<String> home() {
    return ResponseEntity.ok("Hello");
  }

  @GetMapping(value = "/admin", produces = "text/plain")
  public ResponseEntity<String> admin() {
    return ResponseEntity.ok("Hello from admin");
  }

//  @GetMapping(value = "/user", produces = "text/plain")
//  public ResponseEntity<String> user(
//      @CurrentSecurityContext(expression = "authentication?.name") String username) {
//    return ResponseEntity.ok("Hello from User: " + username);
//  }
//
//  @GetMapping(value = "/password/{pass}", produces = "text/plain")
//  public ResponseEntity<String> getPassword(@PathVariable String pass) {
//    return ResponseEntity.ok(passwordEncoderService.getPasswordEncoder().encode(pass));
//  }
}
