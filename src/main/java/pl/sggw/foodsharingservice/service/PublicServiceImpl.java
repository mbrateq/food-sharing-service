package pl.sggw.foodsharingservice.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.ErrorMessages;
import pl.sggw.foodsharingservice.model.dto.CreateUserDto;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.mapper.UserMapper;
import pl.sggw.foodsharingservice.model.repository.UserRepository;
import pl.sggw.foodsharingservice.model.view.UserView;
import pl.sggw.foodsharingservice.security.PasswordEncoderService;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class PublicServiceImpl implements PublicService {

  private final UserRepository userRepository;
  private final PasswordEncoderService passwordEncoderService;
  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  @Override
  public UserView addUser(CreateUserDto createUserDto) {
    userRepository
        .findByUsernameAndToDeleteFalse(createUserDto.getUsername())
        .ifPresent(
            user -> {
              throw new ValidationException(
                  format(
                      ErrorMessages.USER_ALREADY_EXISTS_WITH_USERNAME_MESSAGE,
                      createUserDto.getUsername()));
            });
    return userMapper.toUserView(
        userRepository.save(
            User.builder()
                .username(createUserDto.getUsername())
                .password(
                    passwordEncoderService
                        .getPasswordEncoder()
                        .encode(CharBuffer.wrap(createUserDto.getPassword())))
                .build()));
  }
}
