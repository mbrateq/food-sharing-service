package pl.sggw.foodsharingservice.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.message.ErrorMessages;
import pl.sggw.foodsharingservice.model.dto.UpdatePasswordDto;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.mapper.UserMapper;
import pl.sggw.foodsharingservice.model.repository.UserRepository;
import pl.sggw.foodsharingservice.model.repository.UserRolesRepository;
import pl.sggw.foodsharingservice.model.view.UserView;
import pl.sggw.foodsharingservice.security.PasswordEncoderService;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.nio.CharBuffer;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserRolesRepository userRolesRepository;
  private final PasswordEncoderService passwordEncoderService;
  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  @Override
  public UserView updatePassword(String username, UpdatePasswordDto updatePasswordDto) {
    final User user =
        userRepository
            .findByUsernameAndToDeleteFalse(username)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(ErrorMessages.USER_NOT_EXISTS_WITH_USERNAME_MESSAGE, username)));
    if (passwordEncoderService
        .getPasswordEncoder()
        .matches(CharBuffer.wrap(updatePasswordDto.getOldPassword()), user.getPassword())) {
      return userMapper.toUserView(
          userRepository.save(
              user.toBuilder()
                  .password(
                      passwordEncoderService
                          .getPasswordEncoder()
                          .encode(CharBuffer.wrap(updatePasswordDto.getNewPassword())))
                  .build()));
      //      ADD password reseting Arrays.fill(myArray, null);
    } else {
      throw new ValidationException(ErrorMessages.INVALID_PASSWORD_MESSAGE);
    }
  }

  @Override
  public UserView prepareToDeleteOwnAccount(String username) {
    final User user =
        userRepository
            .findByUsernameAndToDeleteFalse(username)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        format(ErrorMessages.USER_NOT_EXISTS_WITH_USERNAME_MESSAGE, username)));
    userRolesRepository.deleteAll(userRolesRepository.findByUserId(user.getUserId()));
    return userMapper.toUserView(
        userRepository.save(user.toBuilder().enabled(false).toDelete(true).build()));
  }

  @Override
  public Page<UserView> searchUsersByUsername(Optional<String> query, Pageable pageable) {
    if (query.isPresent()) {
      final Page<User> page =
          userRepository.findByUsernameContainingAndToDeleteFalse(query.get(), pageable);
      if (0 == page.stream().count()) {
        throw new EntityNotFoundException(format("Cannot find user with username: %s.", query.get()));
      } else {
        return new PageImpl<>(
            page.stream().map(user -> userMapper.toUserView(user)).collect(Collectors.toList()),
            pageable,
            page.getTotalElements());
      }
    } else {
      final Page<User> page = userRepository.findAll(pageable);
      return new PageImpl<>(
          page.stream().map(user -> userMapper.toUserView(user)).collect(Collectors.toList()),
          pageable,
          page.getTotalElements());
    }
  }
}
