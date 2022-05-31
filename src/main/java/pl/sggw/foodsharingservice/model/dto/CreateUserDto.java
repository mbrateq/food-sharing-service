package pl.sggw.foodsharingservice.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Value
public class CreateUserDto {
    @NotNull(message = "Password cannot be null!")
    @Size(min = 8, max = 20, message = "Username length must be between 8 and 20 chars.")
    private String username;

    @NotNull(message = "Password cannot be null!")
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 chars.")
    private char[] password;

    @NotNull(message = "Phone number cannot be null!")
    @Pattern(regexp = "^[0-9]*$", message = "Phone number can only contain digits.")
    @Size(min = 7, max = 10, message = "Phone number must be between 7 and 10 chars.")
    private String phoneNumber;
}
