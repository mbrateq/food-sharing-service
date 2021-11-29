package pl.sggw.foodsharingservice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sggw.foodsharingservice.model.dto.CreateNoticeDto;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.service.NoticeService;
import pl.sggw.foodsharingservice.web.controller.api.NoticeOperations;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class NoticeController implements NoticeOperations {

    private final NoticeService noticeService;

    @Override
    @GetMapping(value = "/notices", produces = "application/json")
    public ResponseEntity<List<Notice>> listAllNotices() {
        return ResponseEntity.ok(noticeService.listAllNotices());
    }

    @Override
    @PostMapping(value = "/notice", produces = "application/json")
    public ResponseEntity<Notice> createNotice(@RequestBody CreateNoticeDto createNoticeDto
            , @CurrentSecurityContext(expression = "authentication?.name") String username
            )
    {
        return ResponseEntity.ok(noticeService.createNotice(createNoticeDto));
    }

    @Override
    @PutMapping(value = "/notice/{noticeId}", produces = "application/json")
    public ResponseEntity<Notice> updateNotice(@PathVariable long noticeId, @RequestBody CreateNoticeDto createNoticeDto) {
        return ResponseEntity.ok(noticeService.updateNotice(noticeId, createNoticeDto));
    }

    @Override
    @DeleteMapping(value = "/notice/{noticeId}", produces = "application/json")
    public ResponseEntity<Boolean> deleteNotice(@PathVariable long noticeId) {
        return ResponseEntity.ok(noticeService.deleteNotice(noticeId));
    }
}
