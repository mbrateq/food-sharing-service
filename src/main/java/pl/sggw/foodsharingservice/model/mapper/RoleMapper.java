package pl.sggw.foodsharingservice.model.mapper;

import org.mapstruct.Mapper;
import pl.sggw.foodsharingservice.model.entity.Role;
import pl.sggw.foodsharingservice.model.view.RoleView;

@Mapper
public interface RoleMapper {

  RoleView toRoleView(Role role);
}
