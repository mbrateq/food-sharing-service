package pl.sggw.foodsharingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.model.dto.CreateUserDto;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.repository.UserRepository;
import pl.sggw.foodsharingservice.security.PasswordEncoderService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.nio.CharBuffer;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoderService passwordEncoderService;

  @Override
  public Optional<User> findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException());
  }

  @Override
  public User setStatus(long userId, boolean status) {
    User toUpdate = userRepository.getById(userId);
    if (toUpdate.isEnabled() == status) {
      throw new ConcurrentModificationException("Entity already modified");
    }
    return userRepository.save(toUpdate.toBuilder().enabled(status).build());
  }

  @Override
  public List<User> listUsers() {
    return userRepository.findAll();
  }

  @Override
  public boolean deleteUser(long userId) {
    boolean deleted = false;
    if (userRepository.findById(userId).isPresent()) {
      userRepository.deleteById(userId);
      deleted = true;
    }
    return deleted;
  }

  @Override
  public User addUser(CreateUserDto createUserDto) {
    //    TODO fix sequence
    return userRepository.save(
        User.builder()
            .username(createUserDto.getUsername())
            .password(
                passwordEncoderService
                    .getPasswordEncoder()
                    .encode(CharBuffer.wrap(createUserDto.getPassword())))
            .build());
  }
}
