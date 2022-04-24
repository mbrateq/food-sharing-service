package pl.sggw.foodsharingservice.notice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sggw.foodsharingservice.base.IntegrationTestBase;
import pl.sggw.foodsharingservice.model.dto.CreateNoticeDto;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.service.NoticeServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NoticeServiceImplTest extends IntegrationTestBase {

  @Autowired private NoticeServiceImpl noticeService;

  @Test
  void checkIfFindAllReturnsProperResult() {
    //        given
    executeScript("notice/list-all.sql");
    //        when
    List<Notice> notices = noticeService.listAllNotices();
    //        then
    assertThat(notices.size() == 3);
  }

  @Test
  void checkIfProperlyCreatesNewNotice() {
    //        given
    final CreateNoticeDto givenCreateNoticeDto =
        CreateNoticeDto.builder()
            .title("test")
            .content("test")
            .expirationDate(LocalDate.of(2020, 10, 10))
            .build();
    final Notice givenNotice =
        Notice.builder()
            .title("test")
            .content("test")
            .expirationDate(LocalDate.of(2020, 10, 10))
            .build();
    final var author = userRepository.save(User.builder().username("uname").enabled(true).password("pass").build());
    //        when
    Notice notice = noticeService.createNotice(givenCreateNoticeDto);
    //        then
    assertThat(null != notice.getNoticeId());
    assertThat(null != notice.getPublicationDateTime());
    assertThat(givenNotice.getContent().equals(notice.getContent()));
    assertThat(givenNotice.getTitle().equals(notice.getTitle()));
    assertThat(givenNotice.getExpirationDate().equals(notice.getExpirationDate()));
    assertThat(noticeService.listAllNotices().size() == 1);
  }

    @Test
    void checkIfProperlyUpdatesNotice() {
        //        given
        executeScript("notice/create-single-notice.sql");
        final long givenNoticeId = 1;
        final String expectedTitle = "UPDATED";
        final String expectedContent = "UPDATED";
        final LocalDate expectedExpirationDate = LocalDate.of(2022,11,11);
        final CreateNoticeDto givenCreateNoticeDto =
                CreateNoticeDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .expirationDate(expectedExpirationDate)
                .build();
        //        when
        Notice updated = noticeService.updateNotice(givenNoticeId, givenCreateNoticeDto);
        //        then
        assertThat(givenNoticeId==updated.getNoticeId());
        assertThat(expectedTitle.equals(updated.getTitle()));
        assertThat(expectedContent.equals(updated.getContent()));
        assertThat(expectedExpirationDate.equals(updated.getExpirationDate()));
    }

    @Test
    void checkIfProperlyDeletesNotice() {
        //        given
        executeScript("notice/create-single-notice.sql");
        final long givenNoticeId = 10L;
        //        when
        boolean result = noticeService.deleteNotice(givenNoticeId);
        //        then
        assertThat(result);
        assertThat(noticeService.listAllNotices().size() == 0);
    }
}
