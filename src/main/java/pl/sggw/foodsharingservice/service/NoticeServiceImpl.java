package pl.sggw.foodsharingservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.repository.NoticeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    public NoticeRepository noticeRepository;

    @Override
    public List<Notice> listAllNotices() {
        return noticeRepository.findAll();
    }
}
