package pl.sggw.foodsharingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.model.dto.CreateUserDto;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.repository.UserRepository;
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

    @Override
    public User addUser(CreateUserDto createUserDto) {
        userRepository.findByUsername(createUserDto.getUsername()).ifPresent(user -> {
            throw new ValidationException(format("User with username: %s already exists.", createUserDto.getUsername()));
        });
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
