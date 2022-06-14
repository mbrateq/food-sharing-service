package pl.sggw.foodsharingservice.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.message.ErrorMessages;
import pl.sggw.foodsharingservice.model.entity.Role;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.entity.UserRole;
import pl.sggw.foodsharingservice.model.mapper.UserMapper;
import pl.sggw.foodsharingservice.model.repository.RoleRepository;
import pl.sggw.foodsharingservice.model.repository.UserRepository;
import pl.sggw.foodsharingservice.model.repository.UserRolesRepository;
import pl.sggw.foodsharingservice.model.types.RoleType;
import pl.sggw.foodsharingservice.model.view.UserView;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserRolesRepository userRolesRepository;

  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  @Override
  public UserView setStatus(long userId, boolean status) {
    final User toUpdate =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(ErrorMessages.USER_NOT_EXISTS_WITH_ID_MESSAGE, userId)));
    if (toUpdate.isEnabled() != status) {
      return userMapper.toUserView(userRepository.save(toUpdate.toBuilder().enabled(status).build()));
    } else {
      return userMapper.toUserView(toUpdate);
    }
  }

  @Override
  public UserView deleteUserRequest(long userId) {
    final User user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(ErrorMessages.USER_NOT_EXISTS_WITH_ID_MESSAGE, userId)));
    userRolesRepository.deleteAll(userRolesRepository.findByUserId(userId));
    if (!user.isToDelete()) {
      return userMapper.toUserView(userRepository.save(user.toBuilder().toDelete(true).enabled(false).build()));
    } else {
      return userMapper.toUserView(user);
    }
  }

  @Override
  public UserView grantRole(long userId, RoleType roleType) {
    final User user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(ErrorMessages.USER_NOT_EXISTS_WITH_ID_MESSAGE, userId)));
    final Role role =
        roleRepository
            .findByRoleName(roleType.name())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(ErrorMessages.ROLE_NOT_EXISTS_WITH_NAME_MESSAGE, roleType.name())));

    final Optional<UserRole> userRoleOptional = userRolesRepository.findByRoleIdAndUserId(userId, role.getRoleId());
    if (userRoleOptional.isEmpty()) {
      userRolesRepository.save(UserRole.builder().roleId(role.getRoleId()).userId(userId).build());
    }
    return userMapper.toUserView(userRepository.save(user));
  }

  @Override
  public UserView rejectRole(long userId, RoleType roleType) {
//    idempotent???
    final User user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(ErrorMessages.USER_NOT_EXISTS_WITH_ID_MESSAGE, userId)));
    final Role role =
        roleRepository
            .findByRoleName(roleType.name())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(ErrorMessages.ROLE_NOT_EXISTS_WITH_NAME_MESSAGE, roleType.name())));
    final Optional<UserRole> userRoleOptional = userRolesRepository.findByRoleIdAndUserId(userId, role.getRoleId());
    if (userRoleOptional.isPresent()){
      userRolesRepository.delete(userRoleOptional.get());
    }
      return userMapper.toUserView(userRepository.save(user));
  }
}
