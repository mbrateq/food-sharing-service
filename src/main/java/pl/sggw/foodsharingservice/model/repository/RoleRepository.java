package pl.sggw.foodsharingservice.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.sggw.foodsharingservice.model.entity.Role;
import pl.sggw.foodsharingservice.model.entity.User;

import java.util.Optional;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
}
