package pl.sggw.foodsharingservice.web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import pl.sggw.foodsharingservice.model.dto.CreateUserDto;
import pl.sggw.foodsharingservice.model.view.UserView;

@Tag(name = "PUBLIC", description = "Usługi publiczne")
public interface PublicOperations {

  @Operation(summary = "Dodanie nowego użytkownika")
  @ApiResponses({
    @ApiResponse(
        responseCode = "409",
        description = "Użytkownik o podanej nazwie istnieje już w systemie"),
    @ApiResponse(responseCode = "200", description = "Operacja zakończona sukcesem"),
    @ApiResponse(responseCode = "500", description = "Nieznany błąd serwera")
  })
  ResponseEntity<UserView> addUser(CreateUserDto createUserDto);
}
