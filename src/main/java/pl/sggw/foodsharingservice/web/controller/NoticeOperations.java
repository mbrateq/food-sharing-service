package pl.sggw.foodsharingservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import pl.sggw.foodsharingservice.model.dto.CreateNoticeDto;
import pl.sggw.foodsharingservice.model.entity.Notice;

import java.util.List;

@Tag(name = "NOTICES", description = "Ogłoszenia")
public interface NoticeOperations {

    @Operation(summary = "Wyświetlenie komunikatu testowego")
    ResponseEntity<String> home();

    @Operation(summary = "Wyświetlenie listy ogłoszeń")
    ResponseEntity<List<Notice>> listAllNotices();

    @Operation(summary = "Utworzenie nowego ogłoszenia")
    ResponseEntity<Notice> createNotice(CreateNoticeDto createNoticeDto);

    @Operation(summary = "Aktualizacja danych w ogłoszeniu")
    ResponseEntity<Notice> updateNotice(long id, CreateNoticeDto createNoticeDto);

    @Operation(summary = "Usunięcie ogłoszenia")
    ResponseEntity<Boolean> deleteNotice(long id);

}
