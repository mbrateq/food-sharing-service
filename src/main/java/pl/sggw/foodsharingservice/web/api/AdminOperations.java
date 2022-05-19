package pl.sggw.foodsharingservice.web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import pl.sggw.foodsharingservice.model.types.RoleType;
import pl.sggw.foodsharingservice.model.view.UserView;

@Tag(name = "ADMIN", description = "Usługi administracyjne")
public interface AdminOperations {

  @Operation(summary ="Ustawienie statusu użytkownika")
  ResponseEntity<UserView> setStatus(long userId, boolean status);

  @Operation(summary ="Żądanie usunięcia użytkownika")
  ResponseEntity<UserView> deleteUserRequest(long userId);

  @Operation(summary ="Nadanie roli")
  ResponseEntity<UserView> grantRole(long userId, RoleType roleType);

  @Operation(summary ="Odebranie roli")
  ResponseEntity<UserView>  rejectRole(long userId, RoleType roleType);
}
