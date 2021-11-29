package pl.sggw.foodsharingservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sggw.foodsharingservice.model.dto.CreateUserDto;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.service.UserService;
import pl.sggw.foodsharingservice.web.controller.api.UserOperations;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class UserController implements UserOperations {

  private final UserService userService;

  @PutMapping(value = "/user/{userId}", produces = "application/json")
  public ResponseEntity<User> setStatus(@PathVariable long userId, @RequestBody boolean status) {
    return ResponseEntity.ok(userService.setStatus(userId, status));
  }

  @GetMapping(value = "/users", produces = "application/json")
  public ResponseEntity<List<User>> listUsers() {
    return ResponseEntity.ok(userService.listUsers());
  }

  @DeleteMapping(value = "/user/{userId}", produces = "application/json")
  public ResponseEntity<Boolean> deleteUser(@PathVariable long userId) {
    return ResponseEntity.ok(userService.deleteUser(userId));
  }

  @GetMapping(value = "/user/{userName}", produces = "application/json")
  public ResponseEntity<User> getUser(@PathVariable String userName) {
    return ResponseEntity.ok(userService.getUserByUsername(userName));
  }

  @PostMapping(value = "/user", produces = "application/json")
  public ResponseEntity<User> addUser(@RequestBody CreateUserDto createUserDto) {
    return ResponseEntity.ok(userService.addUser(createUserDto));
  }

  @PutMapping(value = "/test1", produces = "application/json")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("test123");
  }
}
