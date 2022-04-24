package pl.sggw.foodsharingservice.web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import pl.sggw.foodsharingservice.model.dto.CreateUserDto;
import pl.sggw.foodsharingservice.model.entity.User;

import java.util.List;

@Tag(name = "PUBLIC", description = "Usługi publiczne")
public interface PublicOperations {

  @Operation(summary ="Dodanie nowego użytkownika")
  ResponseEntity<User> addUser(CreateUserDto createUserDto);

}
