package pl.sggw.foodsharingservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.sggw.foodsharingservice.model.dto.CreateNoticeDto;
import pl.sggw.foodsharingservice.model.dto.UpdateNoticeDto;
import pl.sggw.foodsharingservice.model.view.NoticeView;

import java.util.Optional;

public interface NoticeService {

  Page<NoticeView> searchNoticesByQuery(Optional<String> query, Pageable pageable);

  Page<NoticeView> searchInactiveNoticesByQuery(Optional<String> query, Pageable pageable);

  NoticeView createNotice(CreateNoticeDto createNoticeDto, String username);

  NoticeView updateNotice(long id, UpdateNoticeDto updateNoticeDto , String username);

  boolean deactivateNotice(long id, String username);

  boolean deleteNotice(long id);
}
