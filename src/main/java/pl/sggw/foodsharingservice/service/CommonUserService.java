package pl.sggw.foodsharingservice.service;

import pl.sggw.foodsharingservice.model.dto.UpdatePasswordDto;
import pl.sggw.foodsharingservice.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface CommonUserService {

    Optional<User> findUserByUsername(String username);

    User getUserByUsername(String username);

    User setStatus(long userId, boolean status);

    List<User> listUsers();

    boolean deleteUser(long userId);
}
