package pl.sggw.foodsharingservice.model.dto;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UpdatePasswordDtoTest {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @ParameterizedTest
  @MethodSource("shouldValidateTheObjectData")
  void shouldValidateTheObject(String oldPassword, String newPassword, int violationsNumber) {
    final var updatePasswordDto =
        UpdatePasswordDto.builder()
            .oldPassword((oldPassword != null) ? oldPassword.toCharArray() : null)
            .newPassword((newPassword != null) ? newPassword.toCharArray() : null)
            .build();

    Set<ConstraintViolation<UpdatePasswordDto>> violations = validator.validate(updatePasswordDto);
    assertThat(violations.size()).isEqualTo(violationsNumber);
  }

  static Stream<Arguments> shouldValidateTheObjectData() {
    return Stream.of(
        Arguments.of(null, "password", 1),
        Arguments.of("username", null, 1),
        Arguments.of("username", "password", 0),
        Arguments.of("", "password", 1),
        Arguments.of("username", "", 1),
        Arguments.of("username", "passwor", 1),
        Arguments.of("usernam", "passwor", 1),
        Arguments.of("AAAAAAAAAAAAAAAAAAAA", "BAAAAAAAAAAAAAAAAAAA", 0),
        Arguments.of("AAAAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAA", 1),
        Arguments.of("AAAAAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAB", 1));
  }
}
