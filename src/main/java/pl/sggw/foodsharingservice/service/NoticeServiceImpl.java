package pl.sggw.foodsharingservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.model.dto.CreateNoticeDto;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.mapper.NoticeMapstructMapper;
import pl.sggw.foodsharingservice.model.repository.NoticeRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

  private final NoticeRepository noticeRepository;

  @Override
  public List<Notice> listAllNotices() {
    return noticeRepository.findAll();
  }

  @Override
  public Notice createNotice(CreateNoticeDto createNoticeDto) {
    return noticeRepository.save(
        Notice.builder()
            .title(createNoticeDto.getTitle())
            .content(createNoticeDto.getContent())
            .expirationDate(createNoticeDto.getExpirationDate())
            .publicationDateTime(LocalDateTime.now())
            .build());
  }

  @Override
  public Notice updateNotice(long id, CreateNoticeDto createNoticeDto) {
    Notice notice = noticeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    return noticeRepository.save(
        notice.toBuilder()
            .title(createNoticeDto.getTitle())
            .content(createNoticeDto.getContent())
            .expirationDate(createNoticeDto.getExpirationDate())
            .publicationDateTime(LocalDateTime.now())
            .build());
  }

  @Override
  public boolean deleteNotice(long id) {
    if (noticeRepository.existsById(id)) {
      noticeRepository.deleteById(id);
      return true;
    } else {
      return false;
    }
  }
}
