package pl.sggw.foodsharingservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.service.CommonUserService;
import pl.sggw.foodsharingservice.web.api.AdminOperations;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AdminController implements AdminOperations {

    private final CommonUserService userService;

    @PutMapping(value = "/user/{userId}/status", produces = "application/json")
    public ResponseEntity<User> setStatus(@PathVariable long userId, @RequestBody boolean status) {
        return ResponseEntity.ok(userService.setStatus(userId, status));
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @DeleteMapping(value = "/users/{userId}", produces = "application/json")
    public ResponseEntity<Boolean> deleteUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
