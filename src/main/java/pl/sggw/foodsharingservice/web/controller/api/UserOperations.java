package pl.sggw.foodsharingservice.web.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import java.util.List;

@Tag(name = "USERS", description = "Użytkownicy")
public interface UserOperations {

  @Operation(summary ="Ustawienie statusu użytkownika")
  ResponseEntity<User> setStatus(long userId, boolean status);

  @Operation(summary ="Listowanie użytkowników")
  ResponseEntity<List<User>> listUsers();

  @Operation(summary ="Usunięcie")
  ResponseEntity<Boolean> deleteUser(long userId);

  @Operation(summary ="Ustawienie statusu użytkownika")
  ResponseEntity<User> getUser(String userName);

  @Operation(summary ="Ustawienie statusu użytkownika")
  ResponseEntity<User> addUser(CreateUserDto createUserDto);
}
