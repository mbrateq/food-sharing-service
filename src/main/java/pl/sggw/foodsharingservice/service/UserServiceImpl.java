package pl.sggw.foodsharingservice.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.ErrorMessages;
import pl.sggw.foodsharingservice.model.dto.UpdatePasswordDto;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.mapper.UserMapper;
import pl.sggw.foodsharingservice.model.repository.UserRepository;
import pl.sggw.foodsharingservice.model.view.UserView;
import pl.sggw.foodsharingservice.security.PasswordEncoderService;

import javax.validation.ValidationException;
import java.nio.CharBuffer;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoderService passwordEncoderService;
  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  @Override
  public UserView updatePassword(String username, UpdatePasswordDto updatePasswordDto) {
    final User user =
        userRepository
            .findByUsernameAndToDeleteFalse(username)
            .orElseThrow(
                () ->
                    new ValidationException(
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
                    new ValidationException(
                        format(ErrorMessages.USER_NOT_EXISTS_WITH_USERNAME_MESSAGE, username)));
    return userMapper.toUserView(
        userRepository.save(user.toBuilder().enabled(false).toDelete(true).build()));
  }

  @Override
  public Page<UserView> searchUsersByUsername(Optional<String> query, Pageable pageable) {
    if (query.isPresent()) {
      return new PageImpl<>(
          userRepository.findByUsernameContainingAndToDeleteFalse(query.get(), pageable).stream()
              .map(user -> userMapper.toUserView(user))
              .collect(Collectors.toList()));
    } else {
      return new PageImpl<>(
          userRepository.findAll(pageable).stream()
              .map(user -> userMapper.toUserView(user))
              .collect(Collectors.toList()));
    }
  }
}
