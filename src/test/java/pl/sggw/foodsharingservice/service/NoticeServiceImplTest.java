package pl.sggw.foodsharingservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import pl.sggw.foodsharingservice.message.ErrorMessages;
import pl.sggw.foodsharingservice.base.IntegrationTestBase;
import pl.sggw.foodsharingservice.model.dto.CreateNoticeDto;
import pl.sggw.foodsharingservice.model.dto.UpdateNoticeDto;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.types.CategoryType;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class NoticeServiceImplTest extends IntegrationTestBase {

  @Autowired private NoticeServiceImpl noticeService;

  @Test
  void shouldFindAllActiveNotices() {
    //      given
    final var user =
        userRepository.save(User.builder().username("username").password("password").phone("555555555").build());
    final var expectedSize = 5;
    for (int i = 0; i < expectedSize; i++) {
      final var title = format("title %s", i);
      noticeRepository.save(
          Notice.builder()
              .title(title)
              .content("content")
              .category(CategoryType.DRY_FOOD)
              .expirationDate(LocalDate.now().plusDays(2))
              .publicationDateTime(LocalDateTime.now())
              .author(user)
              .build());
    }
    //    inactive notices (should be skipped)
    for (int i = 0; i < expectedSize; i++) {
      final var title = format("title %s", i);
      noticeRepository.save(
          Notice.builder()
              .title(title)
              .content("content")
              .category(CategoryType.DRY_FOOD)
              .expirationDate(LocalDate.now().plusDays(2))
              .publicationDateTime(LocalDateTime.now())
              .author(user)
              .active(false)
              .build());
    }
    //      when
    final var result = noticeService.searchNoticesByQuery(Optional.empty(), PageRequest.of(0, 20));
    //      then
    assertThat(result.stream().count()).isEqualTo(expectedSize);
  }

  @Test
  void shouldFindAllInactiveNotices() {
    //      given
    final var user =
            userRepository.save(User.builder().username("username").password("password").phone("555555555").build());
    final var expectedSize = 5;
    for (int i = 0; i < expectedSize; i++) {
      final var title = format("title %s", i);
      noticeRepository.save(
              Notice.builder()
                      .title(title)
                      .content("content")
                      .category(CategoryType.DRY_FOOD)
                      .expirationDate(LocalDate.now().plusDays(2))
                      .publicationDateTime(LocalDateTime.now())
                      .author(user)
                      .active(false)
                      .build());
    }
    //    active notices (should be skipped)
    for (int i = 0; i < expectedSize; i++) {
      final var title = format("title %s", i);
      noticeRepository.save(
              Notice.builder()
                      .title(title)
                      .content("content")
                      .category(CategoryType.DRY_FOOD)
                      .expirationDate(LocalDate.now().plusDays(2))
                      .publicationDateTime(LocalDateTime.now())
                      .author(user)
                      .build());
    }
    //      when
    final var result = noticeService.searchNoticesByQuery(Optional.empty(), PageRequest.of(0, 20));
    //      then
    assertThat(result.stream().count()).isEqualTo(expectedSize);
  }

  @ParameterizedTest
  @MethodSource("shouldThrowValidationExceptionWhenQueryIsTooShortData")
  void shouldThrowValidationExceptionWhenQueryIsTooShort(String query) {
    //        when
    Throwable t =
        catchThrowable(
            () ->
                noticeService.searchNoticesByQuery(
                    Optional.ofNullable(query), PageRequest.of(0, 20)));

    //    then
    assertThat(t)
        .isInstanceOf(ValidationException.class)
        .hasMessage(format(ErrorMessages.QUERY_TOO_SHORT_MESSAGE, query));
  }

  @ParameterizedTest
  @MethodSource("shouldThrowValidationExceptionWhenQueryIsTooShortData")
  void shouldThrowValidationExceptionWhenQueryIsTooShortInactive(String query) {
    //        when
    Throwable t =
            catchThrowable(
                    () ->
                            noticeService.searchInactiveNoticesByQuery(
                                    Optional.ofNullable(query), PageRequest.of(0, 20)));

    //    then
    assertThat(t)
            .isInstanceOf(ValidationException.class)
            .hasMessage(format(ErrorMessages.QUERY_TOO_SHORT_MESSAGE, query));
  }

  private static Stream<Arguments> shouldThrowValidationExceptionWhenQueryIsTooShortData() {
    return Stream.of(
        Arguments.of("AA"), Arguments.of("A"), Arguments.of(" A "), Arguments.of("AA "));
  }

  @ParameterizedTest
  @MethodSource("shouldFindNoticesByQueryData")
  void shouldFindNoticesByQuery(String title, String content, String query, int expectedResult) {
    //      given
    final var user =
        userRepository.save(User.builder().username("username").password("pass").phone("555555555").build());
    noticeRepository.save(
        Notice.builder()
            .title(title)
            .content(content)
            .category(CategoryType.DRY_FOOD)
            .expirationDate(LocalDate.now().plusDays(2))
            .publicationDateTime(LocalDateTime.now())
            .author(user)
            .build());
    //    inactive notice (should be skipped)
    noticeRepository.save(
        Notice.builder()
            .title(title)
            .content(content)
            .category(CategoryType.DRY_FOOD)
            .expirationDate(LocalDate.now().plusDays(2))
            .publicationDateTime(LocalDateTime.now())
            .author(user)
            .active(false)
            .build());
    //      when
    final var result =
        noticeService.searchNoticesByQuery(Optional.ofNullable(query), PageRequest.of(0, 20));
    //      then
    assertThat(result.stream().count()).isEqualTo(expectedResult);
  }

  @ParameterizedTest
  @MethodSource("shouldFindNoticesByQueryData")
  void shouldFindInactiveNoticesByQuery(String title, String content, String query, int expectedResult) {
    //      given
    final var user =
            userRepository.save(User.builder().username("username").password("pass").phone("555555555").build());
    noticeRepository.save(
            Notice.builder()
                    .title(title)
                    .content(content)
                    .category(CategoryType.DRY_FOOD)
                    .expirationDate(LocalDate.now().plusDays(2))
                    .publicationDateTime(LocalDateTime.now())
                    .author(user)
                    .active(false)
                    .build());
    //    active notice (should be skipped)
    noticeRepository.save(
            Notice.builder()
                    .title(title)
                    .content(content)
                    .category(CategoryType.DRY_FOOD)
                    .expirationDate(LocalDate.now().plusDays(2))
                    .publicationDateTime(LocalDateTime.now())
                    .author(user)
                    .build());
    //      when
    final var result =
            noticeService.searchNoticesByQuery(Optional.ofNullable(query), PageRequest.of(0, 20));
    //      then
    assertThat(result.stream().count()).isEqualTo(expectedResult);
  }

  private static Stream<Arguments> shouldFindNoticesByQueryData() {
    return Stream.of(
        Arguments.of("AAAAA", "AAAA", "AAA", 1),
        Arguments.of("AAAAA", "AAAA", "aaa", 1),
        Arguments.of("AAAAA", "BBBB", "aaa", 1),
        Arguments.of("BBBB", "AAAA", "aaa", 1),
        Arguments.of("BBBB", "BBBB", "aaa", 0));
  }

  @ParameterizedTest
  @MethodSource("shouldFindWithDateCondition")
  void shouldFindWithDateCondition(LocalDate expirationDate, int expectedResult) {
    //      given
    final var user =
        userRepository.save(User.builder().username("username").password("pass").phone("555555555").build());
    noticeRepository.save(
        Notice.builder()
            .title("title")
            .content("content")
            .category(CategoryType.DRY_FOOD)
            .expirationDate(expirationDate)
            .publicationDateTime(LocalDateTime.now())
            .author(user)
            .build());
    //      when
    final var result = noticeService.searchNoticesByQuery(Optional.empty(), PageRequest.of(0, 20));
    //      then
    assertThat(result.stream().count()).isEqualTo(expectedResult);
  }

  private static Stream<Arguments> shouldFindWithDateCondition() {
    return Stream.of(
        Arguments.of(LocalDate.now(), 1),
        Arguments.of(LocalDate.now().plusDays(1), 1),
        Arguments.of(LocalDate.now().minusDays(1), 0));
  }

  @Test
  void shouldCreateNotice() {
    //    given
    final var user =
        userRepository.save(User.builder().username("username").password("pass").phone("555555555").build());
    final var givenTitle = "TITLE";
    final var givenContent = "CONTENT";
    final var givenCategoryType = CategoryType.DRY_FOOD;
    final var givenExpirationDate = LocalDate.now();
    final var createNoticeDto =
        CreateNoticeDto.builder()
            .title(givenTitle)
            .content(givenContent)
            .categoryType(givenCategoryType)
            .expirationDate(givenExpirationDate)
            .build();

    //    when
    final var notice = noticeService.createNotice(createNoticeDto, user.getUsername());

    //    then
    assertThat(notice.getPublicationDateTime()).isNotNull();
    assertThat(notice.getExpirationDate()).isEqualTo(givenExpirationDate);
    assertThat(notice.getTitle()).isEqualTo(givenTitle);
    assertThat(notice.getContent()).isEqualTo(givenContent);
    assertThat(notice.getCategory()).isEqualTo(givenCategoryType);
    assertThat(notice.getUsername()).isEqualTo(user.getUsername());
    assertThat(notice.isActive()).isTrue();
  }

  @Test
  void shouldThrowValidationExceptionOnAttemptToCreateNoticeWithNonExistingUsername() {
    // given
    final var givenTitle = "TITLE";
    final var givenContent = "CONTENT";
    final var givenCategoryType = CategoryType.DRY_FOOD;
    final var givenExpirationDate = LocalDate.now();
    final var createNoticeDto =
        CreateNoticeDto.builder()
            .title(givenTitle)
            .content(givenContent)
            .categoryType(givenCategoryType)
            .expirationDate(givenExpirationDate)
            .build();
    // when
    Throwable t = catchThrowable(() -> noticeService.createNotice(createNoticeDto, "username"));

    //    then
    assertThat(t)
        .isInstanceOf(ValidationException.class)
        .hasMessage(format(ErrorMessages.USER_NOT_EXISTS_WITH_USERNAME_MESSAGE, "username"));
  }

  @Test
  void shouldUpdateNotice() {
    //    given
    final var user =
        userRepository.save(User.builder().username("username").password("pass").phone("555555555").build());
    final var givenTitle = "TITLE";
    final var givenNewTitle = "NEW_TITLE";
    final var givenContent = "CONTENT";
    final var givenNewContent = "NEW_CONTENT";
    final var givenCategoryType = CategoryType.DRY_FOOD;
    final var givenExpirationDate = LocalDate.now();
    final var givenNewExpirationDate = LocalDate.now().plusDays(1);
    final var createNoticeDto =
        CreateNoticeDto.builder()
            .title(givenTitle)
            .content(givenContent)
            .categoryType(givenCategoryType)
            .expirationDate(givenExpirationDate)
            .build();

    final var updateNoticeDto =
        UpdateNoticeDto.builder()
            .title(givenNewTitle)
            .content(givenNewContent)
            .expirationDate(givenNewExpirationDate)
            .build();

    final var noticeId = noticeService.createNotice(createNoticeDto, "username").getNoticeId();
    // when
    final var notice = noticeService.updateNotice(noticeId, updateNoticeDto, "username");
    //    then
    assertThat(notice.getPublicationDateTime()).isNotNull();
    assertThat(notice.getExpirationDate()).isEqualTo(givenNewExpirationDate);
    assertThat(notice.getTitle()).isEqualTo(givenNewTitle);
    assertThat(notice.getContent()).isEqualTo(givenNewContent);
    assertThat(notice.getCategory()).isEqualTo(givenCategoryType);
    assertThat(notice.getUsername()).isEqualTo(user.getUsername());
  }

  @Test
  void shouldThrowValidationExceptionOnAttemptToUpdateNoticeWithNonExistingUsername() {
    // given
    final var updateNoticeDto = UpdateNoticeDto.builder().build();
    // when
    Throwable t = catchThrowable(() -> noticeService.updateNotice(1L, updateNoticeDto, "username"));

    //    then
    assertThat(t)
        .isInstanceOf(ValidationException.class)
        .hasMessage(format(ErrorMessages.USER_NOT_EXISTS_WITH_USERNAME_MESSAGE, "username"));
  }

  @Test
  void shouldThrowValidationExceptionOnAttemptToUpdateNoticeWithNonExistingNotice() {
    // given
    final var user =
        userRepository.save(User.builder().username("username").password("pass").phone("555555555").build());
    final var updateNoticeDto = UpdateNoticeDto.builder().build();
    // when
    Throwable t = catchThrowable(() -> noticeService.updateNotice(1L, updateNoticeDto, "username"));

    //    then
    assertThat(t)
        .isInstanceOf(ValidationException.class)
        .hasMessage(format(ErrorMessages.NOTICE_NOT_EXISTS_WITH_AUTHOR_ID_MESSAGE, "username", 1L));
  }

  @Test
  void shouldReturnTrueAfterDeletingTheNotice() {
    //    given
    final var user =
        userRepository.save(User.builder().username("username").password("pass").phone("555555555").build());
    final var givenTitle = "TITLE";
    final var givenContent = "CONTENT";
    final var givenCategoryType = CategoryType.DRY_FOOD;
    final var givenExpirationDate = LocalDate.now();
    final var notice =
        noticeRepository.save(
            Notice.builder()
                .title(givenTitle)
                .content(givenContent)
                .category(givenCategoryType)
                .expirationDate(givenExpirationDate)
                .publicationDateTime(LocalDateTime.now())
                .author(user)
                .build());

    //    when
    final var result = noticeService.deleteNotice(notice.getNoticeId());

    //    then
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseOnAttemptToDeleteTheNonExistingNotice() {
    //    given

    //    when
    final var result = noticeService.deleteNotice(1L);

    //    then
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnTrueAfterDeactivatingTheNotice() {
    //    given
    final var user =
        userRepository.save(User.builder().username("username").password("pass").phone("555555555").build());
    final var givenTitle = "TITLE";
    final var givenContent = "CONTENT";
    final var givenCategoryType = CategoryType.DRY_FOOD;
    final var givenExpirationDate = LocalDate.now();
    final var givenNotice =
        noticeRepository.save(
            Notice.builder()
                .title(givenTitle)
                .content(givenContent)
                .category(givenCategoryType)
                .expirationDate(givenExpirationDate)
                .publicationDateTime(LocalDateTime.now())
                .author(user)
                .build());

    //    when
    final var result = noticeService.deactivateNotice(givenNotice.getNoticeId(), "username");

    //    then
    final var notice = noticeRepository.findById(1L).get();
    assertThat(result).isTrue();
    assertThat(notice.getPublicationDateTime()).isNotNull();
    assertThat(notice.getExpirationDate()).isEqualTo(givenExpirationDate);
    assertThat(notice.getTitle()).isEqualTo(givenTitle);
    assertThat(notice.getContent()).isEqualTo(givenContent);
    assertThat(notice.getCategory()).isEqualTo(givenCategoryType);
    assertThat(notice.getAuthor().getUsername()).isEqualTo(user.getUsername());
    assertThat(notice.isActive()).isFalse();
  }

  @Test
  void shouldThrowValidationExceptionOnAttemptToDeactivateNoticeWithNonExistingUser() {
    // given
    // when
    Throwable t = catchThrowable(() -> noticeService.deactivateNotice(1L, "username"));

    //    then
    assertThat(t)
        .isInstanceOf(ValidationException.class)
        .hasMessage(format(ErrorMessages.USER_NOT_EXISTS_WITH_USERNAME_MESSAGE, "username"));
  }

  @Test
  void shouldThrowValidationExceptionOnAttemptToDeactivateNoticeWithBadUsernameOrId() {
    // given
    final var user =
        userRepository.save(User.builder().username("username").password("pass").phone("555555555").build());
    // when
    Throwable t = catchThrowable(() -> noticeService.deactivateNotice(2L, "username"));

    //    then
    assertThat(t)
        .isInstanceOf(ValidationException.class)
        .hasMessage(format(ErrorMessages.NOTICE_NOT_EXISTS_WITH_AUTHOR_ID_MESSAGE, "username", 2L));
  }
}
