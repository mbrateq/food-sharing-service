package pl.sggw.foodsharingservice.service;

import pl.sggw.foodsharingservice.model.dto.CreateNoticeDto;
import pl.sggw.foodsharingservice.model.entity.Notice;

import java.util.List;

public interface NoticeService {

  //    convert to dtos and paging
  List<Notice> listAllNotices();

  Notice createNotice(CreateNoticeDto createNoticeDto);

  Notice updateNotice(long id, CreateNoticeDto createNoticeDto);

  boolean deleteNotice(long id);
}
