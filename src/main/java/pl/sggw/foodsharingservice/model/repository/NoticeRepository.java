package pl.sggw.foodsharingservice.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.entity.User;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface NoticeRepository extends PagingAndSortingRepository<Notice,Long>, JpaSpecificationExecutor {
    Page<Notice> findByTitleContainsIgnoreCaseOrContentContainsIgnoreCaseAndExpirationDateBefore(String title, String content, LocalDate expirationDate, Pageable pageable);
    Page<Notice> findByExpirationDateBefore(LocalDate expirationDate, Pageable pageable);
    Optional<Notice> findByAuthorAndAndNoticeId(User author, long noticeId);
}
