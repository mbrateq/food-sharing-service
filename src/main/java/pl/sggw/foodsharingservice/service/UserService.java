package pl.sggw.foodsharingservice.service;

import pl.sggw.foodsharingservice.model.dto.UpdatePasswordDto;
import pl.sggw.foodsharingservice.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User updatePassword(String username, UpdatePasswordDto updatePasswordDto);

    User prepareToDeleteOwnAccount(String username);
}
