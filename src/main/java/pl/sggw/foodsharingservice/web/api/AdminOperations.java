package pl.sggw.foodsharingservice.web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import pl.sggw.foodsharingservice.model.dto.CreateNoticeDto;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.entity.User;

import java.util.List;

@Tag(name = "ADMIN", description = "Usługi administracyjne")
public interface AdminOperations {

  @Operation(summary ="Ustawienie statusu użytkownika")
  ResponseEntity<User> setStatus(long userId, boolean status);

  @Operation(summary ="Listowanie użytkowników")
  ResponseEntity<List<User>> listUsers();

  @Operation(summary ="Usunięcie użytkownika")
  ResponseEntity<Boolean> deleteUser(long userId);
}
