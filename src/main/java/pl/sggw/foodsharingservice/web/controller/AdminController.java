package pl.sggw.foodsharingservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sggw.foodsharingservice.model.types.RoleType;
import pl.sggw.foodsharingservice.model.view.UserView;
import pl.sggw.foodsharingservice.service.AdminService;
import pl.sggw.foodsharingservice.web.api.AdminOperations;

@Slf4j
@RestController
@RequestMapping("api/v1/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController implements AdminOperations {

  private final AdminService adminService;

  @PutMapping(value = "/user/{userId}/status", produces = "application/json")
  public ResponseEntity<UserView> setStatus(@PathVariable long userId, @RequestBody boolean status) {
    return ResponseEntity.ok(adminService.setStatus(userId, status));
  }

  @DeleteMapping(value = "/users/{userId}", produces = "application/json")
  public ResponseEntity<UserView> deleteUserRequest(@PathVariable long userId) {
    return ResponseEntity.ok(adminService.deleteUserRequest(userId));
  }

  @PutMapping(value = "/user/{userId}/role", produces = "application/json")
  public ResponseEntity<UserView> grantRole(long userId, RoleType roleType) {
    return ResponseEntity.ok(adminService.grantRole(userId, roleType));
  }

  @DeleteMapping(value = "/user/{userId}/role", produces = "application/json")
  public ResponseEntity<UserView> rejectRole(long userId, RoleType roleType) {
    return ResponseEntity.ok(adminService.rejectRole(userId, roleType));
  }
}
