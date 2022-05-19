package pl.sggw.foodsharingservice.model.dto;

import lombok.Builder;
import lombok.Value;
import pl.sggw.foodsharingservice.model.dto.annotation.PasswordValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Value
@PasswordValid
public class UpdatePasswordDto {

    @NotEmpty(message = "Old password cannot be null!")
    private char[] oldPassword;

    @NotNull(message = "New password cannot be null!")
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 chars.")
    private char[] newPassword;
}
