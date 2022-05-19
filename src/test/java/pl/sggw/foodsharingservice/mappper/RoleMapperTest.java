package pl.sggw.foodsharingservice.mappper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.sggw.foodsharingservice.model.entity.Role;
import pl.sggw.foodsharingservice.model.mapper.RoleMapper;
import pl.sggw.foodsharingservice.model.types.RoleType;
import pl.sggw.foodsharingservice.model.view.RoleView;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RoleMapperTest {

  private final RoleMapper mapper = Mappers.getMapper(RoleMapper.class);

  @Test
  void toRoleViewTest() {
    //    given
    final var givenRoleId = 1L;
    final var givenRolename = RoleType.ROLE_USER.name();
    final var givenEntity = Role.builder().roleId(givenRoleId).roleName(givenRolename).build();
    final var expectedResult =
        RoleView.builder().roleId(givenRoleId).roleName(givenRolename).build();

    // when
    final var result = mapper.toRoleView(givenEntity);

    //    then
    assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
  }
}
