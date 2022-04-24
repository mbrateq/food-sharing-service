package pl.sggw.foodsharingservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.sggw.foodsharingservice.base.IntegrationTestBase;
import pl.sggw.foodsharingservice.model.dto.CreateUserDto;

import javax.validation.ValidationException;

import java.nio.CharBuffer;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;


class PublicServiceImplTest extends IntegrationTestBase {

    @Autowired private PublicService publicService;

    @Test
    void shouldAddUserIfNotExist() {
//        given
        final var expectedId = 1L;
        final var  givenUsername = "test";
        final var  givenPassword = "secret";
//        final var givenEncryptedPassword = "$2a$10$BDZZBL9AC79zFh3Icu4dfe/OVyf8TiBuwcNaW7BxBV8HeueYOL/pK";
        final var createUserDto = CreateUserDto.builder().username(givenUsername).password(givenPassword.toCharArray()).build();

//        when
        publicService.addUser(createUserDto);
        final var result = userRepository.findAll();

//        then
        assertThat(result.size()).isEqualTo(1);
        final var resultUser = result.get(0);
        assertThat(resultUser.getUsername()).isEqualTo(givenUsername);
//        assertThat(resultUser.getPassword()).isEqualTo(givenEncryptedPassword);
        assertThat(resultUser.isEnabled()).isFalse();
        assertThat(resultUser.isToDelete()).isFalse();
        assertThat(resultUser.getRoles().isEmpty()).isTrue();
    }

    @Test
    void shouldThrowValidationExceptionIfUserAlreadyExists() {
//        given
        final var  givenUsername = "test";
        final var  givenPassword = "secret";
        final var createUserDto = CreateUserDto.builder().username(givenUsername).password(givenPassword.toCharArray()).build();
        publicService.addUser(createUserDto);
//        when
        Throwable t = catchThrowable(() -> publicService.addUser(createUserDto));

//        then
        assertThat(t).isInstanceOf(ValidationException.class).hasMessage("User with username: test already exists.");
    }
}