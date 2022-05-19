package pl.sggw.foodsharingservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sggw.foodsharingservice.model.dto.CreateNoticeDto;
import pl.sggw.foodsharingservice.model.dto.UpdateNoticeDto;
import pl.sggw.foodsharingservice.model.view.NoticeView;
import pl.sggw.foodsharingservice.service.NoticeService;
import pl.sggw.foodsharingservice.web.api.NoticeOperations;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class NoticeController extends ControllerAbstract implements NoticeOperations {

  private final NoticeService noticeService;

  @Override
  @GetMapping(value = "/notices", produces = "application/json")
  public ResponseEntity<Page<NoticeView>> listAllNotices(
      @RequestParam(required = false, name = "query") Optional<String> query,
      @RequestParam(name = "per-page", defaultValue = "20", required = false) int pageSize,
      @RequestParam(name = "page", defaultValue = "1", required = false) int page) {
    return ResponseEntity.ok(
        noticeService.searchNoticesByQuery(query, preparePageRequest(page, pageSize)));
  }

  @Override
  @GetMapping(value = "/notices/inactive", produces = "application/json")
  public ResponseEntity<Page<NoticeView>> listAllInactiveNotices(
      @RequestParam(required = false, name = "query") Optional<String> query,
      @RequestParam(name = "per-page", defaultValue = "20", required = false) int pageSize,
      @RequestParam(name = "page", defaultValue = "1", required = false) int page) {
    return ResponseEntity.ok(
        noticeService.searchNoticesByQuery(query, preparePageRequest(page, pageSize)));
  }

  @Override
  @PostMapping(value = "/notice", produces = "application/json")
  public ResponseEntity<NoticeView> createNotice(
      @RequestBody CreateNoticeDto createNoticeDto,
      @CurrentSecurityContext(expression = "authentication?.name") String username) {
    return ResponseEntity.ok(noticeService.createNotice(createNoticeDto, username));
  }

  @Override
  @PutMapping(value = "/notice/{noticeId}", produces = "application/json")
  public ResponseEntity<NoticeView> updateNotice(
      @PathVariable long noticeId,
      @RequestBody UpdateNoticeDto updateNoticeDto,
      @CurrentSecurityContext(expression = "authentication?.name") String username) {
    return ResponseEntity.ok(noticeService.updateNotice(noticeId, updateNoticeDto, username));
  }

  @Override
  @DeleteMapping(value = "/notice/{noticeId}/deactivation", produces = "application/json")
  public ResponseEntity<Boolean> deactivateNotice(
      @PathVariable long noticeId,
      @CurrentSecurityContext(expression = "authentication?.name") String username) {
    return ResponseEntity.ok(noticeService.deactivateNotice(noticeId, username));
  }

  @Override
  @DeleteMapping(value = "/notice/{noticeId}", produces = "application/json")
  public ResponseEntity<Boolean> deleteNotice(@PathVariable long noticeId) {
    return ResponseEntity.ok(noticeService.deleteNotice(noticeId));
  }
}
