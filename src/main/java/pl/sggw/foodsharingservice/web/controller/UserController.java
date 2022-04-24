package pl.sggw.foodsharingservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sggw.foodsharingservice.model.dto.UpdatePasswordDto;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.service.CommonUserService;
import pl.sggw.foodsharingservice.service.UserService;
import pl.sggw.foodsharingservice.web.api.UserOperations;

@Slf4j
@RestController
@RequestMapping("api/v1")
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class UserController implements UserOperations {

  private final UserService userService;

  @Override
  @DeleteMapping(value = "/user", produces = "application/json")
  public ResponseEntity<User> selfDelete(@CurrentSecurityContext(expression = "authentication?.name") String username) {
    return ResponseEntity.ok(userService.prepareToDeleteOwnAccount(username));
  }

  @Override
  @PutMapping(value = "/user", produces = "application/json")
  public ResponseEntity<User> updatePassword(@CurrentSecurityContext(expression = "authentication?.name") String username, @RequestBody UpdatePasswordDto updatePasswordDto) {
    return ResponseEntity.ok(userService.updatePassword(username,updatePasswordDto));
  }
}
