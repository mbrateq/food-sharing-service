package pl.sggw.foodsharingservice.mappper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.sggw.foodsharingservice.model.entity.Role;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.mapper.UserMapper;
import pl.sggw.foodsharingservice.model.types.RoleType;
import pl.sggw.foodsharingservice.model.view.RoleView;
import pl.sggw.foodsharingservice.model.view.UserView;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserMapperTest {

  private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

  @Test
  void toUserViewTest() {
    //    given
    final var givenId = 1L;
    final var givenRoleName = RoleType.ROLE_USER.name();
    final var givenRole = Role.builder().roleName(givenRoleName).roleId(givenId).build();
    final var givenRolesSet = Collections.singleton(givenRole);
    final var givenUsername = "USERNAME";
    final var givenPhone = "555555555";
    final var givenEnabled = true;
    final var givenToDelete = false;

    final var givenRoleView = RoleView.builder().roleId(givenId).roleName(givenRoleName).build();
    final var givenRoleViewsSet = Collections.singleton(givenRoleView);

    final var givenEntity =
        User.builder()
            .userId(givenId)
            .username(givenUsername)
            .enabled(givenEnabled)
            .toDelete(givenToDelete)
            .roles(givenRolesSet)
            .phone(givenPhone)
            .build();

    final var expectedResult =
        UserView.builder()
            .userId(givenId)
            .username(givenUsername)
            .enabled(givenEnabled)
            .toDelete(givenToDelete)
            .roles(givenRoleViewsSet)
            .phone(givenPhone)
            .build();

    // when
    final var result = mapper.toUserView(givenEntity);

    //    then
    assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
  }
}
