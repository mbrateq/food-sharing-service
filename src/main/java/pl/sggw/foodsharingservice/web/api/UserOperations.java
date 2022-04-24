package pl.sggw.foodsharingservice.web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import pl.sggw.foodsharingservice.model.dto.CreateUserDto;
import pl.sggw.foodsharingservice.model.dto.UpdatePasswordDto;
import pl.sggw.foodsharingservice.model.entity.User;

import java.util.List;

@Tag(name = "USERS", description = "Użytkownicy")
public interface UserOperations {

  @Operation(summary ="Usunięcie konta")
  ResponseEntity<User> selfDelete(String username);

  @Operation(summary ="Zmiana hasła")
  ResponseEntity<User> updatePassword(String username, UpdatePasswordDto updatePasswordDto);

}
