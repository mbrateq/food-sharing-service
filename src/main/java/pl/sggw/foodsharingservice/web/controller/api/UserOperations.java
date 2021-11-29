package pl.sggw.foodsharingservice.web.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import pl.sggw.foodsharingservice.model.dto.CreateUserDto;
import pl.sggw.foodsharingservice.model.entity.User;

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

  @Operation(summary = "test dokumentacja", description = "Opis metody")
  ResponseEntity<String> test();
}
