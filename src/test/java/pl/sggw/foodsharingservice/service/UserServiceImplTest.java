package pl.sggw.foodsharingservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import pl.sggw.foodsharingservice.message.ErrorMessages;
import pl.sggw.foodsharingservice.base.IntegrationTestBase;
import pl.sggw.foodsharingservice.model.dto.UpdatePasswordDto;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.security.PasswordEncoderService;

import javax.validation.ValidationException;
import java.nio.CharBuffer;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class UserServiceImplTest extends IntegrationTestBase {

  @Autowired private UserServiceImpl userService;
  @Autowired private PasswordEncoderService passwordEncoderService;

  @Test
  void shouldUpdatePasswordForUser() {
    //        given
    final var givenOldPassword = "secret";
    final var givenNewPassword = "secret2";
    final var user =
        userRepository.save(
            User.builder()
                .username("username")
                .password(
                    passwordEncoderService
                        .getPasswordEncoder()
                        .encode(CharBuffer.wrap(givenOldPassword.toCharArray())))
                .phone("555555555")
                .build());
    final var updatePasswordDto =
        UpdatePasswordDto.builder()
            .oldPassword(givenOldPassword.toCharArray())
            .newPassword(givenNewPassword.toCharArray())
            .build();

    //        when
    userService.updatePassword("username", updatePasswordDto);

    //    then
    final var result = userRepository.findByUsernameAndToDeleteFalse("username").get();
    assertThat(
            passwordEncoderService
                .getPasswordEncoder()
                .matches(CharBuffer.wrap(givenNewPassword), result.getPassword()))
        .isTrue();
  }

  @Test
  void shouldThrowValidationExceptionWhileTryingToUpdatePasswordForNonExistingUser() {
    //        when
    Throwable t =
        catchThrowable(
            () -> userService.updatePassword("username", UpdatePasswordDto.builder().build()));

    //    then
    assertThat(t)
        .isInstanceOf(ValidationException.class)
        .hasMessage(format(ErrorMessages.USER_NOT_EXISTS_WITH_USERNAME_MESSAGE, "username"));
  }

  @Test
  void shouldThrowValidationExceptionWhileProvidingInvalidPassword() {
    //        given
    final var givenOldPassword = "secret";
    final var givenWrongPassword = "bad";
    final var givenNewPassword = "secret2";
    final var user =
        userRepository.save(
            User.builder()
                .username("username")
                .password(
                    passwordEncoderService
                        .getPasswordEncoder()
                        .encode(CharBuffer.wrap(givenOldPassword.toCharArray())))
                .phone("555555555")
                .build());
    final var updatePasswordDto =
        UpdatePasswordDto.builder()
            .oldPassword(givenWrongPassword.toCharArray())
            .newPassword(givenNewPassword.toCharArray())
            .build();

    //        when
    Throwable t = catchThrowable(() -> userService.updatePassword("username", updatePasswordDto));

    //    then
    assertThat(t)
        .isInstanceOf(ValidationException.class)
        .hasMessage(ErrorMessages.INVALID_PASSWORD_MESSAGE);
  }

  @Test
  void shouldPrepareToDelete() {
    final var givenPassword = "secret";
    final var givenUsername = "username";
    final var user =
        userRepository.save(
            User.builder()
                .username("username")
                .password(
                    passwordEncoderService
                        .getPasswordEncoder()
                        .encode(CharBuffer.wrap(givenPassword.toCharArray())))
                .phone("555555555")
                .enabled(true)
                .toDelete(false)
                .build());

    //      when
    userService.prepareToDeleteOwnAccount(givenUsername);
    //      then
    final var result = userRepository.findByUsernameAndToDeleteTrue(givenUsername).get();
    assertThat(result.isToDelete()).isTrue();
    assertThat(result.isEnabled()).isFalse();
  }

  @Test
  void shouldThrowValidationExceptionWhileTryingDeleteNonExistingUser() {
    //        when
    Throwable t = catchThrowable(() -> userService.prepareToDeleteOwnAccount("username"));

    //    then
    assertThat(t)
        .isInstanceOf(ValidationException.class)
        .hasMessage(format(ErrorMessages.USER_NOT_EXISTS_WITH_USERNAME_MESSAGE, "username"));
  }

  @Test
  void shouldFindAllUsers() {
    //      given
    final var expectedSize = 5;
    for (int i = 0; i < expectedSize; i++) {
      final var username = format("test%s", i);
      userRepository.save(
          User.builder().username(username).password("pass").phone("555555555").build());
    }
    //      when
    final var result = userService.searchUsersByUsername(Optional.empty(), PageRequest.of(0, 20));
    //      then
    assertThat(result.stream().count()).isEqualTo(expectedSize);
  }

  @ParameterizedTest
  @MethodSource("shouldFindAllUsersByQueryData")
  void shouldFindAllUsersByQuery(String username, String query, int expectedResult) {
    //      given
    userRepository.save(
        User.builder().username(username).phone("5555555555").password("pass").build());

    //      when
    final var result =
        userService.searchUsersByUsername(Optional.ofNullable(query), PageRequest.of(0, 20));
    //      then
    assertThat(result.stream().count()).isEqualTo(expectedResult);
  }

  private static Stream<Arguments> shouldFindAllUsersByQueryData() {
    return Stream.of(
        Arguments.of("username", "username", 1),
        Arguments.of("username", "rna", 1),
        Arguments.of("username", "abc", 0),
        Arguments.of("blablabla", "bla", 1));
  }
}
