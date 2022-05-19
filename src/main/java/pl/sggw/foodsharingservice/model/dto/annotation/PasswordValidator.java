package pl.sggw.foodsharingservice.model.dto.annotation;

import pl.sggw.foodsharingservice.model.dto.UpdatePasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordValidator implements ConstraintValidator<PasswordValid, UpdatePasswordDto> {
  public void initialize(PasswordValid constraintAnnotation) {}

  public boolean isValid(UpdatePasswordDto object, ConstraintValidatorContext context) {
    if (!(object instanceof UpdatePasswordDto)) {
      throw new IllegalArgumentException(
          "@AuthMethodValidation only applies to UpdatePasswordDto objects");
    }
    UpdatePasswordDto updatePasswordDto = object;
    return (!Arrays.equals(updatePasswordDto.getOldPassword(), updatePasswordDto.getNewPassword()));
  }
}
