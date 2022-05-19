package pl.sggw.foodsharingservice.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.sggw.foodsharingservice.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByUsernameAndToDeleteFalse(String username);

    Optional<User> findByUsernameAndToDeleteTrue(String username);

    Page<User> findByUsernameContainingAndToDeleteFalse(String username, Pageable pageable);

    List<User> findByToDeleteTrue();

}
