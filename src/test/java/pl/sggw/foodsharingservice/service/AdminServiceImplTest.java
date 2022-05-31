package pl.sggw.foodsharingservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sggw.foodsharingservice.message.ErrorMessages;
import pl.sggw.foodsharingservice.base.IntegrationTestBase;
import pl.sggw.foodsharingservice.model.entity.Role;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.types.RoleType;

import javax.validation.ValidationException;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class AdminServiceImplTest extends IntegrationTestBase {

  @Autowired private AdminServiceImpl adminService;

  @ParameterizedTest
  @MethodSource("shouldSetStatusForUserData")
  void shouldSetStatusForUser(
      boolean initialStatus, boolean updatedStatus, boolean expectedStatus) {
    //        given
    final var user =
        userRepository.save(
            User.builder().username("username").password("secret").enabled(initialStatus).phone("555555555").build());
    final var userId = user.getUserId();

    //        when
    adminService.setStatus(user.getUserId(), updatedStatus);

    //    then
    final var result = userRepository.findByUsernameAndToDeleteFalse("username").get();
    assertThat(result.isEnabled()).isEqualTo(expectedStatus);
  }

  private static Stream<Arguments> shouldSetStatusForUserData() {
    return Stream.of(
        Arguments.of(false, true, true),
        Arguments.of(false, false, false),
        Arguments.of(true, false, false),
        Arguments.of(true, true, true));
  }

  @Test
  void shouldThrowValidationExceptionWhileTryingToSetStatusForNonExistingUser() {
    //        when
    Throwable t = catchThrowable(() -> adminService.setStatus(1, true));

    //    then
    assertThat(t)
        .isInstanceOf(ValidationException.class)
        .hasMessage(format(ErrorMessages.USER_NOT_EXISTS_WITH_ID_MESSAGE, 1));
  }

  @Test
  void shouldDeleteUser() {
    //    given
    final var user =
        userRepository.save(
            User.builder().username("username").password("password").phone("555555555").enabled(true).build());
    final var userid = user.getUserId();

    //    when
    adminService.deleteUserRequest(userid);

    //    then
    final var result = userRepository.findById(userid).get();
    assertThat(result.isEnabled()).isFalse();
    assertThat(result.isToDelete()).isTrue();
  }

  @Test
  void shouldThrowValidationExceptionWhileTryingToDeleteNonExistingUser() {
    //        when
    Throwable t = catchThrowable(() -> adminService.deleteUserRequest(1));

    //    then
    assertThat(t)
        .isInstanceOf(ValidationException.class)
        .hasMessage(format(ErrorMessages.USER_NOT_EXISTS_WITH_ID_MESSAGE, 1));
  }

  @Test
  void shouldGrantRole() {
    //    given
    final var role = roleRepository.save(Role.builder().roleName("ROLE_ADMIN").build());
    final var user =
        userRepository.save(
            User.builder().username("username").password("password").phone("555555555").enabled(true).build());

    //    when
    adminService.grantRole(user.getUserId(), RoleType.ROLE_ADMIN);

    //    then
    final var result = userRepository.findById(user.getUserId()).get().getRoles();
    assertThat(result.size()).isEqualTo(1);
    assertThat(result.contains(role));
  }

  @Test
  void shouldThrowValidationExceptionWhileTryingToGrantRoleToNonExistingUser() {

    //        when
    Throwable t = catchThrowable(() -> adminService.grantRole(1, RoleType.ROLE_ADMIN));

    //    then
    assertThat(t)
            .isInstanceOf(ValidationException.class)
            .hasMessage(format(ErrorMessages.USER_NOT_EXISTS_WITH_ID_MESSAGE, 1));
  }

  @Test
  void shouldThrowValidationExceptionWhileTryingToGrantNonExistingRole() {
    final var user =
            userRepository.save(
                    User.builder().username("username").password("password").phone("555555555").enabled(true).build());

    //        when
    Throwable t = catchThrowable(() -> adminService.grantRole(1, RoleType.ROLE_ADMIN));

    //    then
    assertThat(t)
            .isInstanceOf(ValidationException.class)
            .hasMessage(format(ErrorMessages.ROLE_NOT_EXISTS_WITH_NAME_MESSAGE, RoleType.ROLE_ADMIN.name()));
  }

  @Test
  void shouldRejectRole() {
    //    given
    final var user =
            userRepository.save(
                    User.builder().username("username").password("password").phone("555555555").enabled(true).build());
    roleRepository.save(Role.builder().roleName("ROLE_ADMIN").build());
    adminService.grantRole(user.getUserId(), RoleType.ROLE_ADMIN);

    //    when
    adminService.rejectRole(user.getUserId(), RoleType.ROLE_ADMIN);

    //    then
    final var result = userRepository.findById(user.getUserId()).get().getRoles();
    assertThat(result.size()).isEqualTo(0);
  }

  @Test
  void shouldThrowValidationExceptionWhileTryingToRejectRoleFromNonExistingUser() {

    //        when
    Throwable t = catchThrowable(() -> adminService.rejectRole(1, RoleType.ROLE_ADMIN));

    //    then
    assertThat(t)
            .isInstanceOf(ValidationException.class)
            .hasMessage(format(ErrorMessages.USER_NOT_EXISTS_WITH_ID_MESSAGE, 1));
  }

  @Test
  void shouldThrowValidationExceptionWhileTryingToRejectNonExistingRole() {
    final var user =
            userRepository.save(
                    User.builder().username("username").password("password").phone("555555555").enabled(true).build());

    //        when
    Throwable t = catchThrowable(() -> adminService.rejectRole(1, RoleType.ROLE_ADMIN));

    //    then
    assertThat(t)
            .isInstanceOf(ValidationException.class)
            .hasMessage(format(ErrorMessages.ROLE_NOT_EXISTS_WITH_NAME_MESSAGE, RoleType.ROLE_ADMIN.name()));
  }

}
