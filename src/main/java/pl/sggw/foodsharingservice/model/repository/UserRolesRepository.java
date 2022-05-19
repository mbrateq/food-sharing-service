package pl.sggw.foodsharingservice.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.entity.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRolesRepository extends PagingAndSortingRepository<UserRole, Long> {

    Optional<UserRole> findByRoleIdAndUserId(long roleId, long userId);

    List<UserRole> findByUserId(long userId);

}
