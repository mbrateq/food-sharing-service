package pl.sggw.foodsharingservice.mappper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sggw.foodsharingservice.model.dto.NoticeView;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.mapper.NoticeMapstructMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NoticeMapperTest {

  @Autowired private NoticeMapstructMapper mapper;

  @Test
  void toNoticeViewTest() {
    //        given
    Notice givenNotice =
        Notice.builder()
            .noticeId(1L)
            .title("TITLE")
            .content("CONTENT")
            .expirationDate(LocalDate.of(2020, 10, 10))
            .publicationDateTime(LocalDateTime.of(2021, 12, 12, 20, 10))
            .build();
    //    when
    NoticeView noticeView = mapper.toNoticeView(givenNotice);
    //    then
    assertAll(
        "Should map Notice to NoticeView",
        () -> assertEquals(givenNotice.getNoticeId(), noticeView.getNoticeId()),
        () -> assertEquals(givenNotice.getTitle(), noticeView.getTitle()),
        () -> assertEquals(givenNotice.getContent(), noticeView.getContent()),
        () -> assertEquals(givenNotice.getExpirationDate(), noticeView.getExpirationDate()),
        () ->
            assertEquals(
                givenNotice.getPublicationDateTime(), noticeView.getPublicationDateTime()));
  }
}
