package pl.sggw.foodsharingservice.service;

import pl.sggw.foodsharingservice.model.entity.User;

public interface UserService {

    User findUserByEmail(String email);
}
