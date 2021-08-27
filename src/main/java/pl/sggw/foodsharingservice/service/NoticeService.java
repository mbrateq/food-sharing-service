package pl.sggw.foodsharingservice.service;

import pl.sggw.foodsharingservice.model.entity.Notice;

import java.util.List;

public interface NoticeService {
//    convert to dtos and paging
    List<Notice> listAllNotices();
}
