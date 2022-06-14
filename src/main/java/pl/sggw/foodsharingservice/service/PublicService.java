package pl.sggw.foodsharingservice.service;

import pl.sggw.foodsharingservice.model.dto.CreateUserDto;
import pl.sggw.foodsharingservice.model.entity.User;
import pl.sggw.foodsharingservice.model.view.UserView;

import java.util.List;
import java.util.Optional;

public interface PublicService {

    UserView addUser(CreateUserDto createUserDto);

}
