package pl.sggw.foodsharingservice.model.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CreateUserDtoTest {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @ParameterizedTest
  @MethodSource("shouldValidateTheObjectData")
  void shouldValidateTheObject(String username, String password, int violationsNumber) {
    final var createUserDto =
        CreateUserDto.builder()
            .username(username)
            .password((password != null) ? password.toCharArray() : null)
            .build();

    Set<ConstraintViolation<CreateUserDto>> violations = validator.validate(createUserDto);
    assertThat(violations.size()).isEqualTo(violationsNumber);
  }

  static Stream<Arguments> shouldValidateTheObjectData() {
    return Stream.of(
        Arguments.of(null, "password", 1),
        Arguments.of("username", null, 1),
        Arguments.of("username", "password", 0),
        Arguments.of("", "password", 2),
        Arguments.of("username", "", 1),
        Arguments.of("username", "passwor", 1),
        Arguments.of("usernam", "passwor", 2),
        Arguments.of("qwertyuiopasdfghjkla", "qwertyuiopasdfghjka", 0),
        Arguments.of("qwertyuiopasdfghjklap", "qwertyuiopasdfghjklap", 2));
  }
}
