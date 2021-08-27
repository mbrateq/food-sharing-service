package pl.sggw.foodsharingservice.notice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sggw.foodsharingservice.base.IntegrationTestBase;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.service.NoticeServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NoticeServiceImplTest extends IntegrationTestBase {

    @Autowired
    private NoticeServiceImpl noticeService;

    @Test
    void checkIfFindAllReturnsProperResult(){
//        given
        clearDatabase();
        executeScript("notice/list-all.sql");
//        when
        List<Notice> notices = noticeService.listAllNotices();
//        then
        assertThat(notices.size()==0);
    }
}
