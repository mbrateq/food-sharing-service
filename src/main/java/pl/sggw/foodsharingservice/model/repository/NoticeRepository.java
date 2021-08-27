package pl.sggw.foodsharingservice.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sggw.foodsharingservice.model.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
