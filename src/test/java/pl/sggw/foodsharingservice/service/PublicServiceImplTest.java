package pl.sggw.foodsharingservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.sggw.foodsharingservice.ErrorMessages;
import pl.sggw.foodsharingservice.base.IntegrationTestBase;
import pl.sggw.foodsharingservice.model.dto.CreateUserDto;
import pl.sggw.foodsharingservice.security.PasswordEncoderService;

import javax.validation.ValidationException;

import java.nio.CharBuffer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

class PublicServiceImplTest extends IntegrationTestBase {

  @Autowired private PublicService publicService;
  @Autowired private PasswordEncoderService passwordEncoderService;

  @Test
  void shouldAddUserIfNotExist() {
    //        given
    final var expectedId = 1L;
    final var givenUsername = "test";
    final var givenPassword = "secret";
    final var createUserDto =
        CreateUserDto.builder()
            .username(givenUsername)
            .password(givenPassword.toCharArray())
            .phoneNumber("555555555")
            .build();

    //        when
    publicService.addUser(createUserDto);
    final var result =
        StreamSupport.stream(userRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());

    //        then
    assertThat(result.size()).isEqualTo(1);
    final var resultUser = result.get(0);
    assertThat(resultUser.getUsername()).isEqualTo(givenUsername);
    assertThat(
            passwordEncoderService
                .getPasswordEncoder()
                .matches(CharBuffer.wrap(givenPassword.toCharArray()), resultUser.getPassword()))
        .isTrue();
    assertThat(resultUser.isEnabled()).isFalse();
    assertThat(resultUser.isToDelete()).isFalse();
    assertThat(resultUser.getRoles().isEmpty()).isTrue();
  }

  @Test
  void shouldThrowValidationExceptionIfUserAlreadyExists() {
    //        given
    final var givenUsername = "test";
    final var givenPassword = "secret";
    final var createUserDto =
        CreateUserDto.builder()
            .username(givenUsername)
            .password(givenPassword.toCharArray())
            .phoneNumber("555555555")
            .build();
    publicService.addUser(createUserDto);
    //        when
    Throwable t = catchThrowable(() -> publicService.addUser(createUserDto));

    //        then
    assertThat(t)
        .isInstanceOf(ValidationException.class)
        .hasMessage(format(ErrorMessages.USER_ALREADY_EXISTS_WITH_USERNAME_MESSAGE, givenUsername));
  }
}
