package pl.sggw.foodsharingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.model.dto.UpdatePasswordDto;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.repository.UserRepository;
import pl.sggw.foodsharingservice.security.PasswordEncoderService;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoderService;

    @Override
    public User updatePassword(String username, UpdatePasswordDto updatePasswordDto) {
        final User user = userRepository.findByUsername(username).orElseThrow(() -> new ValidationException("User with username: %s not found."));
        if (passwordEncoderService.getPasswordEncoder().matches(CharBuffer.wrap(updatePasswordDto.getOldPassword()), user.getPassword())) {
            return userRepository.save(user.toBuilder().password(passwordEncoderService.getPasswordEncoder().encode(CharBuffer.wrap(updatePasswordDto.getNewPassword()))).build());
        } else {
            throw new ValidationException("Password not valid!");
        }
    }

    @Override
    public User prepareToDeleteOwnAccount(String username) {
        final User user = userRepository.findByUsername(username).orElseThrow(() -> new ValidationException("User with username: %s not found."));
        return userRepository.save(user.toBuilder().enabled(false).toDelete(true).build());
    }
}
