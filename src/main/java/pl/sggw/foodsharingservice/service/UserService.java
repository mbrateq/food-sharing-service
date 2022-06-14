package pl.sggw.foodsharingservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.sggw.foodsharingservice.model.dto.UpdatePasswordDto;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.view.UserView;

import java.util.Optional;

public interface UserService {

    UserView updatePassword(String username, UpdatePasswordDto updatePasswordDto);

    UserView prepareToDeleteOwnAccount(String username);

    Page<UserView> searchUsersByUsername(Optional<String> query, Pageable pageable);
}
