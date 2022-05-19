package pl.sggw.foodsharingservice.model.dto;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.sggw.foodsharingservice.model.types.CategoryType;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CreateNoticeDtoTest {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @ParameterizedTest
  @MethodSource("shouldValidateTheObjectData")
  void shouldValidateTheObject(
      String title,
      String content,
      LocalDate expirationDate,
      CategoryType categoryType,
      int violationsNumber) {
    final var createNoticeDto =
        CreateNoticeDto.builder()
            .title(title)
            .content(content)
            .expirationDate(expirationDate)
            .categoryType(categoryType)
            .build();

    Set<ConstraintViolation<CreateNoticeDto>> violations = validator.validate(createNoticeDto);
    assertThat(violations.size()).isEqualTo(violationsNumber);
  }

  static Stream<Arguments> shouldValidateTheObjectData() {
    return Stream.of(
        Arguments.of("TITLE", "TEST_CONTENT", LocalDate.now().plusDays(1), CategoryType.FOOD, 0),
        Arguments.of(null, "TEST_CONTENT", LocalDate.now().plusDays(1), CategoryType.FOOD, 1),
        Arguments.of("", "TEST_CONTENT", LocalDate.now().plusDays(1), CategoryType.FOOD, 1),
        Arguments.of(
            "AAAAAAAAAA_AAAAAAAAAA_AAAAAAAA_",
            "TEST_CONTENT",
            LocalDate.now().plusDays(1),
            CategoryType.FOOD,
            1),
        Arguments.of("AAAA", "TEST_CONTENT", LocalDate.now().plusDays(1), CategoryType.FOOD, 1),
        Arguments.of("TITLE", null, LocalDate.now().plusDays(1), CategoryType.FOOD, 1),
        Arguments.of("TITLE", "", LocalDate.now().plusDays(1), CategoryType.FOOD, 1),
        Arguments.of("TITLE", "AAAAAAAAA", LocalDate.now().plusDays(1), CategoryType.FOOD, 1),
        Arguments.of("TITLE", "TEST_CONTENT", LocalDate.now().minusDays(1), CategoryType.FOOD, 1),
        Arguments.of("TITLE", "TEST_CONTENT", LocalDate.now(), CategoryType.FOOD, 1),
        Arguments.of("TITLE", "TEST_CONTENT", LocalDate.now().plusDays(1), null, 1));
  }
}
