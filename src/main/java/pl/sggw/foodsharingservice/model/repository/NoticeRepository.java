package pl.sggw.foodsharingservice.model.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends PagingAndSortingRepository<Notice,Long>, JpaSpecificationExecutor {
    Optional<Notice> findByAuthorAndAndNoticeId(User author, long noticeId);
    List<Notice> findByExpirationDateBefore(LocalDate expirationDate);
    List<Notice> findByAuthor(User author);
}
