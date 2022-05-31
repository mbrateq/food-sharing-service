package pl.sggw.foodsharingservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sggw.foodsharingservice.model.dto.UpdatePasswordDto;
import pl.sggw.foodsharingservice.model.view.UserView;
import pl.sggw.foodsharingservice.service.UserService;
import pl.sggw.foodsharingservice.web.api.UserOperations;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class UserController extends ControllerAbstract implements UserOperations {

  private final UserService userService;

  @Override
  @DeleteMapping(value = "/user", produces = "application/json")
  public ResponseEntity<UserView> selfDelete(
      @CurrentSecurityContext(expression = "authentication?.name") String username) {
    return ResponseEntity.ok(userService.prepareToDeleteOwnAccount(username));
  }

  @Override
  @PutMapping(value = "/user", produces = "application/json")
  public ResponseEntity<UserView> updatePassword(
      @CurrentSecurityContext(expression = "authentication?.name") String username,
      @RequestBody UpdatePasswordDto updatePasswordDto) {
    return ResponseEntity.ok(userService.updatePassword(username, updatePasswordDto));
  }

  @Override
  @GetMapping(value = "/", produces = "application/json")
  public ResponseEntity<Page<UserView>> searchUsersByUsername(
      @RequestParam(required = false, name = "query") Optional<String> query,
      @RequestParam(name = "per-page", defaultValue = "20", required = false) int pageSize,
      @RequestParam(name = "page", defaultValue = "1", required = false) int page) {
    return ResponseEntity.ok(
        userService.searchUsersByUsername(query, preparePageRequest(page, pageSize)));
  }
}
