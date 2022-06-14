package pl.sggw.foodsharingservice.web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import pl.sggw.foodsharingservice.model.dto.UpdatePasswordDto;
import pl.sggw.foodsharingservice.model.view.UserView;

import java.util.Optional;

@Tag(name = "USERS", description = "Użytkownicy")
public interface UserOperations {

  @Operation(summary ="Usunięcie konta")
  ResponseEntity<UserView> selfDelete(String username);

  @Operation(summary ="Zmiana hasła")
  ResponseEntity<UserView> updatePassword(String username, UpdatePasswordDto updatePasswordDto);

  @Operation(summary ="Wyszukanie użytkowników po nazwie.")
  ResponseEntity<Page<UserView>> searchUsersByUsername(Optional<String> query, int pageSize, int page);

}
