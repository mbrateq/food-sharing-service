package pl.sggw.foodsharingservice.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sggw.foodsharingservice.base.IntegrationTestBase;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.entity.Role;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.entity.UserRole;
import pl.sggw.foodsharingservice.model.types.CategoryType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SelfMaintenanceEngineImplTest extends IntegrationTestBase {

  @Autowired private SelfMaintenanceEngineImpl selfMaintenanceEngine;

  @Test
  void shouldDeleteExpiredNotices() {
    //        given
    final var user =
        userRepository.save(User.builder().username("username").password("password").phone("555555555").build());
    for (int i = 0; i < 10; i++) {
      noticeRepository.save(
          Notice.builder()
              .title("TITLE")
              .content("TEXT_CONTENT")
              .category(CategoryType.FOOD)
              .expirationDate(LocalDate.now().minusDays(1))
              .publicationDateTime(LocalDateTime.now())
              .author(user)
              .build());
    }
    for (int i = 0; i < 3; i++) {
      noticeRepository.save(
          Notice.builder()
              .title("TITLE")
              .content("TEXT_CONTENT")
              .category(CategoryType.FOOD)
              .expirationDate(LocalDate.now())
              .publicationDateTime(LocalDateTime.now())
              .author(user)
              .build());
    }

    //    when
    selfMaintenanceEngine.removeExpiredNotices();

    //    then
    assertThat(StreamSupport.stream(noticeRepository.findAll().spliterator(), false).count())
        .isEqualTo(3);
  }

  @Test
  void shouldRemoveDeletedUsers() {
    //      given
    final var user =
        userRepository.save(
            User.builder().username("username").password("password").phone("555555555").toDelete(true).build());

    roleRepository.save(Role.builder().roleName("ROLENAME").build());
    userRolesRepository.save(UserRole.builder().roleId(1L).userId(user.getUserId()).build());
    noticeRepository.save(
        Notice.builder()
            .title("TITLE")
            .content("TEXT_CONTENT")
            .category(CategoryType.FOOD)
            .expirationDate(LocalDate.now())
            .publicationDateTime(LocalDateTime.now())
            .author(user)
            .build());

    //    when
    selfMaintenanceEngine.removeDeletedUsers();

    //    then
      assertThat(StreamSupport.stream(noticeRepository.findAll().spliterator(), false).count()).isEqualTo(0);
      assertThat(StreamSupport.stream(userRepository.findAll().spliterator(), false).count()).isEqualTo(0);
      assertThat(StreamSupport.stream(userRolesRepository.findAll().spliterator(), false).count()).isEqualTo(0);
  }
}
