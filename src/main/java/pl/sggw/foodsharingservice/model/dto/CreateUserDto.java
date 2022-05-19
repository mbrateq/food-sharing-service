package pl.sggw.foodsharingservice.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Value
public class CreateUserDto {
    @NotBlank(message = "Username cannot be empty.")
    @Size(min = 8, max = 20, message = "Username length must be between 8 and 20 chars.")
    private String username;

    @NotNull(message = "Password cannot be null!")
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 chars.")
    private char[] password;
}
