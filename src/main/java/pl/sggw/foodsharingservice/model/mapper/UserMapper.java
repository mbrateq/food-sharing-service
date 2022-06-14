package pl.sggw.foodsharingservice.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.view.UserView;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {

  @Mapping(source = "user.roles", target = "roles")
  UserView toUserView(User user);
}
