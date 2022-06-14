package pl.sggw.foodsharingservice.model.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.mapper.NoticeMapper;
import pl.sggw.foodsharingservice.model.types.CategoryType;
import pl.sggw.foodsharingservice.model.view.NoticeView;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NoticeMapperTest {

  private final NoticeMapper mapper = Mappers.getMapper(NoticeMapper.class);

  @Test
  void toNoticeViewTest() {

    //        given
    final var givenNoticeId = 1L;
    final var givenTitle = "TITLE";
    final var givenContent = "CONTENT";
    final var givenExpirationDate = LocalDate.now();
    final var givenPublicationDateTime = LocalDateTime.now();
    final var givenActive = true;
    final var givenCategory = CategoryType.DRY_FOOD;
    final var givenUsername = "USERNAME";
    final var givenAuthor = User.builder().username(givenUsername).build();

    final var givenEntity =
        Notice.builder()
            .noticeId(givenNoticeId)
            .title(givenTitle)
            .content(givenContent)
            .expirationDate(givenExpirationDate)
            .publicationDateTime(givenPublicationDateTime)
            .active(givenActive)
            .category(givenCategory)
            .author(givenAuthor)
            .build();
    final var expectedResult =
        NoticeView.builder()
            .noticeId(givenNoticeId)
            .title(givenTitle)
            .content(givenContent)
            .expirationDate(givenExpirationDate)
            .publicationDateTime(givenPublicationDateTime)
            .active(givenActive)
            .category(givenCategory)
            .username(givenUsername)
            .build();

    //    when
    final var result = mapper.toNoticeView(givenEntity);

    //    then
      assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
  }
}
