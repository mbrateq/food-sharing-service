package pl.sggw.foodsharingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CommonUserServiceImpl implements CommonUserService {

    private final UserRepository userRepository;

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
        final User toUpdate = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
        if (toUpdate.isEnabled() != status) {
            return userRepository.save(toUpdate.toBuilder().enabled(status).build());
        } else {
            return toUpdate;
        }
    }//Activate user???

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
}
