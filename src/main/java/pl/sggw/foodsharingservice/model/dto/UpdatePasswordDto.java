package pl.sggw.foodsharingservice.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Builder
@Value
public class UpdatePasswordDto {
    @NotBlank(message = "Old password cannot be empty!")
    private char[] oldPassword;

    @NotBlank(message = "New password cannot be empty!")
    private char[] newPassword;
}
