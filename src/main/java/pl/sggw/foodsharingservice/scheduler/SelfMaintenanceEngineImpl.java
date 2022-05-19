package pl.sggw.foodsharingservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.model.entity.Notice;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.repository.NoticeRepository;
import pl.sggw.foodsharingservice.model.repository.UserRepository;
import pl.sggw.foodsharingservice.model.repository.UserRolesRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SelfMaintenanceEngineImpl implements SelfMaintenanceEngine {

  private final NoticeRepository noticeRepository;
  private final UserRepository userRepository;
  private final UserRolesRepository userRolesRepository;

  @Override
  public void removeExpiredNotices() {
    List<Notice> noticesToDelete = noticeRepository.findByExpirationDateBefore(LocalDate.now());
    noticeRepository.deleteAll(noticesToDelete);
  }

  @Override
  public void removeDeletedUsers() {
    List<User> usersToDelete = userRepository.findByToDeleteTrue();

    usersToDelete.stream()
        .forEach(
            user -> {
              noticeRepository.deleteAll(noticeRepository.findByAuthor(user));
              userRolesRepository.deleteAll(userRolesRepository.findByUserId(user.getUserId()));
            });
    userRepository.deleteAll(usersToDelete);
  }
}
