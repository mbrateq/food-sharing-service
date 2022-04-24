package pl.sggw.foodsharingservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sggw.foodsharingservice.model.dto.CreateUserDto;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.service.PublicService;
import pl.sggw.foodsharingservice.web.api.PublicOperations;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PublicController implements PublicOperations {

    private final PublicService publicService;

    @PostMapping(value = "register", produces = "application/json")
    public ResponseEntity<User> addUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(publicService.addUser(createUserDto));
    }
}
