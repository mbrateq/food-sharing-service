package pl.sggw.foodsharingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByUsername(email).get();
    }
}
