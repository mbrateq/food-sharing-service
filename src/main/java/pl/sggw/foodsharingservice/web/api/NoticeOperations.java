package pl.sggw.foodsharingservice.web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import pl.sggw.foodsharingservice.model.dto.CreateNoticeDto;
import pl.sggw.foodsharingservice.model.dto.UpdateNoticeDto;
import pl.sggw.foodsharingservice.model.view.NoticeView;

import java.util.Optional;

@Tag(name = "NOTICES", description = "Ogłoszenia")
public interface NoticeOperations {

  @Operation(summary = "Wyświetlenie listy ogłoszeń")
  ResponseEntity<Page<NoticeView>> listAllNotices(Optional<String> query, int pageSize, int page);

  @Operation(summary = "Wyświetlenie listy ogłoszeń nieaktywnych - admin")
  ResponseEntity<Page<NoticeView>> listAllInactiveNotices(Optional<String> query, int pageSize, int page);

  @Operation(summary = "Utworzenie nowego ogłoszenia")
  ResponseEntity<NoticeView> createNotice(CreateNoticeDto createNoticeDto, String username);

  @Operation(summary = "Aktualizacja danych w ogłoszeniu")
  ResponseEntity<NoticeView> updateNotice(long id, UpdateNoticeDto updateNoticeDto, String username);

  @Operation(summary = "Usunięcie ogłoszenia - deaktywacja przez autora")
  ResponseEntity<Boolean> deactivateNotice(long id, String username);

//  Move to admin
  @Operation(summary = "Usunięcie ogłoszenia - admin")
  ResponseEntity<Boolean> deleteNotice(long id);
}
